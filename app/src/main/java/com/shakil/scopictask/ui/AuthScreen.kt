package com.shakil.scopictask.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun AuthScreen(navController: NavHostController, notesViewModel: NotesViewModel) {
    val destination = notesViewModel.destinationToLaunch
    DisposableEffect(key1 = Unit, effect = {
        notesViewModel.getInitialDestitination()
        onDispose {  }
    })

    if(destination.value != null) {
        val destinationToLaunch = destination.value!!
        navController.navigate(destinationToLaunch)
    }
}