package com.shakil.scopictask.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.shakil.scopictask.ui.theme.Red200
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ProfileScreen(navController: NavHostController, notesViewModel: NotesViewModel) {

    val email = notesViewModel.preferences.userEmail.collectAsState(initial = "")
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 8.dp)
                    .requiredHeight(56.dp)
            ) {

                Text(
                    text = "Profile",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp)
                )

                Text(
                    text = "Back",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Red200,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp, end = 20.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )

            }

        },
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp, bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = email.value, style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontSize = 30.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        notesViewModel.signOut {
                            navController.navigate("login_page") {
                                popUpTo("list_page") {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Log Out", fontSize = 20.sp)
                }

            }
        },

        )
}