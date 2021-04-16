package com.shakil.scopictask.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shakil.scopictask.domain.Notes
import com.shakil.scopictask.preference.ScopicPref
import com.shakil.scopictask.repo.NotesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class NotesViewModel @Inject constructor(
     val preferences: ScopicPref,
     val repo: NotesRepo,
     val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var firebaseAuth = Firebase.auth
    private val firebaseDb = Firebase.firestore

    val prefNotes = preferences.notes
    var destinationToLaunch: MutableState<String?> = mutableStateOf(null)
    var showAddNoteDialog by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var authError by mutableStateOf("")

    //Fields
    //Login
    var emailValue by mutableStateOf("")
    var passwordValue by mutableStateOf("")
    var passwordVisibility by mutableStateOf(false)

    //Sign Up
    var registerEmailValue by mutableStateOf("")
    var registerPasswordValue by mutableStateOf("")
    var registerPasswordVisibility by mutableStateOf(false)

    var noteText by mutableStateOf("")
    var isListFromFirebase by mutableStateOf(false)
    var authComplete by mutableStateOf(false)


    fun signInUser(email: String, password: String) {
        loading = true
        viewModelScope.launch {
            try {
                val loginUser = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (loginUser != null) {
                    loginUser.let {
                        it.user?.let { user ->
                            emailValue  = ""
                            passwordValue = ""
                            passwordVisibility = false
                            preferences.saveUserEmail(user.email ?: "")
                            preferences.saveUserId(user.uid ?: "")
                            authComplete = true
                        }
                    }
                } else {
                    authError = "Authentication failed."
                }
            } catch (e: FirebaseAuthException) {
                authError = e.localizedMessage ?: "Authentication failed."
            } catch (e: Exception) {
                authError = "Authentication failed."
            } finally {
                loading = false
            }
        }
    }

    fun markWelComeScreen() {
        viewModelScope.launch {
            preferences.markWelcomeSeen()
        }
    }

    fun signOut(complete:() -> Unit) {
        viewModelScope.launch {
            preferences.clearAllData()
            firebaseAuth.signOut()
            withContext(Main) {
                complete()
            }
        }
    }

    fun createNewUser(email: String, password: String) {
        loading = true
        viewModelScope.launch {
            try {
                val loginUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                if (loginUser != null) {
                    loginUser.let {
                        it.user?.let { user ->
                            registerEmailValue  = ""
                            registerPasswordValue = ""
                            registerPasswordVisibility = false
                            preferences.saveUserEmail(user.email ?: "")
                            preferences.saveUserId(user.uid ?: "")
                            authComplete = true
                        }
                    }
                } else {
                    authError = "Authentication failed."
                }
            } catch (e: FirebaseAuthException) {
                authError = e.localizedMessage ?: "Authentication failed."
            } catch (e: Exception) {
                authError = "Authentication failed."
            } finally {
                loading = false
            }
        }
    }

    fun addNoteToPreference(note: String) {
        viewModelScope.launch {
            val list = preferences.notes.first().toMutableList()
            list.add(Notes(note,UUID.randomUUID().toString()))
            preferences.saveNotes(notes = list.toList())
        }
    }

    fun removeNoteFromPref(noteID: String) {
        viewModelScope.launch {
            val list = preferences.notes.first().toMutableList()
            val saveNote = list.find {
                it.id == noteID
            }
            if(saveNote != null){
                list.remove(saveNote)
            }
            preferences.saveNotes(notes = list.toList())
        }
    }

    fun addNotesToFirebase(note: String) {
        viewModelScope.launch {
            val userId = preferences.userID.first()
            val id = UUID.randomUUID().toString()
            val data = hashMapOf("text" to note, "id" to id)
            firebaseDb.collection("users").document(userId).collection("Notes").document(id).set(data)
        }

    }

    fun removeNoteFromFirebase(id: String) {
        viewModelScope.launch {
            val userId = preferences.userID.first()
            firebaseDb.collection("users").document(userId).collection("Notes").document(id).delete()
        }
    }


    fun getInitialDestitination() {
        viewModelScope.launch {
            val userId = preferences.userID.first()
            val welcomeSeen = preferences.welcomeSeen.first()
            if(userId.isNotEmpty()) {
                if(welcomeSeen) {
                    destinationToLaunch.value = "list_page"
                } else {
                    destinationToLaunch.value = "welcome_page"
                }
            } else {
                destinationToLaunch.value = "login_page"
            }
        }
    }
}