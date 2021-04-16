package com.shakil.scopictask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.shakil.scopictask.ui.*
import com.shakil.scopictask.ui.theme.Red200
import com.shakil.scopictask.ui.theme.ScopicTaskTheme
import com.shakil.scopictask.ui.theme.TransBlack
import com.shakil.scopictask.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @InternalCoroutinesApi
    private val notesViewModel: NotesViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScopicTaskTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "auth_page", builder = {
                    composable(
                        "auth_page",
                        content = { AuthScreen(navController = navController, notesViewModel) })
                    composable(
                        "login_page",
                        content = { LoginScreen(navController = navController, notesViewModel) })
                    composable(
                        "register_page",
                        content = { RegisterScreen(navController = navController, notesViewModel) })
                    composable(
                        "welcome_page",
                        content = { WelcomScreen(navController = navController, notesViewModel) })
                    composable(
                        "list_page",
                        content = { ListScreen(navController = navController, notesViewModel) })
                    composable(
                        "profile_page",
                        content = { ProfileScreen(navController = navController, notesViewModel) })
                })

                if (notesViewModel.showAddNoteDialog) {
                    AddNoteDialog(notesViewModel)
                }

                if (notesViewModel.loading) {
                    LoaderView()
                }

                if (notesViewModel.authError.isNotEmpty()) {
                    ErrorView(notesViewModel)
                }

                if(notesViewModel.authComplete) {
                    navController.navigate("auth_page") {
                        popUpTo("login_page") {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}


@InternalCoroutinesApi
@Composable
fun ErrorView(notesViewModel: NotesViewModel) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = notesViewModel.authError)

        },
        confirmButton = {
            Text(text = "Cancel", Modifier.clickable {
                notesViewModel.authError = ""
            })
        }
    )
}

@Composable
fun LoaderView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TransBlack)
    ) {
        Card(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(12.dp),
            elevation = 8.dp,
            backgroundColor = Color.White,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.Center)
            )
        }
    }
}

@InternalCoroutinesApi
@Composable
fun AddNoteDialog(notesViewModel: NotesViewModel) {
    val focusRequester = FocusRequester()
    AlertDialog(
        onDismissRequest = { notesViewModel.noteText = "" },
        title = { 
          Text(text = "")  
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.requiredHeight(16.dp))

                OutlinedTextField(
                    value = notesViewModel.noteText,
                    onValueChange = {
                        if (it.length < 41) {
                            notesViewModel.noteText = it
                        }
                    },
                    label = { Text("Add Note") },
                    placeholder = { Text(text = "Type Here...") },
                    maxLines = 4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester)

                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.CenterEnd),
                    text = "${notesViewModel.noteText.length}/40"
                )
            }

        },
        dismissButton = {
            Text(
                text = "Save",
                style = TextStyle(color = Red200, fontSize = 16.sp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        notesViewModel.showAddNoteDialog = false
                        if (notesViewModel.isListFromFirebase) {
                            notesViewModel.addNotesToFirebase(notesViewModel.noteText)
                        } else {
                            notesViewModel.addNoteToPreference(notesViewModel.noteText)
                        }
                        notesViewModel.noteText = ""
                    })
        },
        confirmButton = {
            Text(text = "Cancel",
                Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        notesViewModel.showAddNoteDialog = false
                        notesViewModel.noteText = ""
                    })
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScopicTaskTheme {
    }
}