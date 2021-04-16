package com.shakil.scopictask.repo

import com.google.firebase.firestore.FirebaseFirestore
import com.shakil.scopictask.domain.Notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class NotesRepo {
    private val firestore = FirebaseFirestore.getInstance()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getList(userId: String) = callbackFlow {
        val collection = firestore.collection("users").document(userId).collection("Notes")
        val snapshotListener = collection.addSnapshotListener { snapshot, error ->
            val notes = ArrayList<Notes>()
            if(snapshot != null && !snapshot.isEmpty) {
                for (doc in snapshot) {
                    val note = doc.toObject(Notes::class.java)
                    notes.add(note)
                }
            }
            offer(notes)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}