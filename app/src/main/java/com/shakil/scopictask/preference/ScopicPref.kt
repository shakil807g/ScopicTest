package com.shakil.scopictask.preference

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shakil.scopictask.domain.Notes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "scopic_pref")

@Singleton
class ScopicPref @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    companion object {
        val NOTES_KEY = stringPreferencesKey(name = "notes")
        val USER_ID_KEY = stringPreferencesKey(name = "user_id")
        val USER_EMAIL_KEY = stringPreferencesKey(name = "email")
        val WELCOME_SCREEN_SEEN = booleanPreferencesKey(name = "welcome_screen_seen")
    }

    suspend fun saveNotes(notes: List<Notes>) {
        context.dataStore.edit { preferences ->
            val list = gson.toJson(notes)
            preferences[NOTES_KEY] = list
        }
    }


    suspend fun markWelcomeSeen() {
        context.dataStore.edit { preferences ->
            preferences[WELCOME_SCREEN_SEEN] = true
        }
    }


    suspend fun markUserLogIn(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    val userID: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_ID_KEY] ?: ""
    }

    val userEmail: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY] ?: ""
    }

    val welcomeSeen: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[WELCOME_SCREEN_SEEN] ?: false
    }


    val notes: Flow<List<Notes>> = context.dataStore.data
        .map { preferences ->
            val notesJson = preferences[NOTES_KEY] ?: ""
            val type = object : TypeToken<List<Notes>>() {}.type
            val notes = try {
                gson.fromJson(notesJson, type)
            } catch (e: Exception) {
                emptyList<Notes>()
            }
            notes
     }

}
