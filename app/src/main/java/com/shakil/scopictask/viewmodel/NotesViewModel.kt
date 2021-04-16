package com.shakil.scopictask.viewmodel

import androidx.compose.runtime.*
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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val preferences: ScopicPref,
    private val repo: NotesRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var firebaseAuth = Firebase.auth
    private val firebaseDb = Firebase.firestore

    val prefNotes = preferences.notes
    var destinationToLaunch: MutableState<String?> = mutableStateOf(null)
    var firebaseNotes: MutableState<List<Notes>> = mutableStateOf(emptyList())
    var showAddNoteDialog by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var authError by mutableStateOf("")
    var loginUserId by mutableStateOf("")
    var loginUserEmail by mutableStateOf("")

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

    var authComplete by mutableStateOf(false)


    fun signInUser(email: String, password: String) {
        loading = true
        viewModelScope.launch {
            try {
                val loginUser = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                if (loginUser != null) {
                    loginUser.let {
                        it.user?.let {
                            loginUserId = it.uid
                            loginUserEmail = it.email ?: ""
                            preferences.saveUserEmail(loginUserEmail)
                            preferences.markUserLogIn(loginUserId)
                            fetchNotes(loginUserId)
                            authComplete = true
                        }
                    }
                } else {
                    authError = "Authentication failed."
                }
            } catch (e: FirebaseAuthException) {
                authError = e.localizedMessage ?: "Authentication failed."
            } finally {
                loading = false
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
                        it.user?.let {
                            loginUserId = it.uid
                            loginUserEmail = it.email ?: ""
                            preferences.saveUserEmail(loginUserEmail)
                            preferences.markUserLogIn(loginUserId)
                            fetchNotes(loginUserId)
                            authComplete = true
                        }
                    }
                } else {
                    authError = "Authentication failed."
                }
            } catch (e: FirebaseAuthException) {
                authError = e.localizedMessage ?: "Authentication failed."
            } finally {
                loading = false
            }
        }
    }

    fun fetchNotes(userId: String) {
        viewModelScope.launch {
            repo.getList(userId).collect {
                firebaseNotes.value = it
            }
        }
    }

    fun saveNote(noteID: String) {
        viewModelScope.launch {
            val list = preferences.notes.first().toMutableList()
            val saveNote = list.find { it.id == noteID }
            if(saveNote != null){
                list.remove(saveNote)
            }
            preferences.saveNotes(n = list.toList())
        }
    }

    fun addNotes(note: String) {
        val data = hashMapOf("text" to note,
            "id" to UUID.randomUUID().toString())
        firebaseDb.collection("users").document(loginUserId).collection("Notes").add(data)
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