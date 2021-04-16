package com.shakil.scopictask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.shakil.scopictask.R
import com.shakil.scopictask.ui.theme.Grey
import com.shakil.scopictask.ui.theme.Red200
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavHostController, notesViewModel: NotesViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {

            Text(
                text = "Scopic",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    color = Grey
                ),
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.padding(50.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Sign In",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),

                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.padding(20.dp))

                OutlinedTextField(
                    value = notesViewModel.emailValue,
                    onValueChange = { notesViewModel.emailValue = it },
                    label = { Text(text = "Email Address") },
                    placeholder = { Text(text = "Email Address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notesViewModel.passwordValue,
                    onValueChange = { notesViewModel.passwordValue = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            notesViewModel.passwordVisibility = !notesViewModel.passwordVisibility
                        }) {
                            Icon(
                                contentDescription = null,
                                imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
                                tint = if (notesViewModel.passwordVisibility) Red200 else Color.Gray
                            )
                        }
                    },
                    label = { Text("Password") },
                    placeholder = { Text(text = "Password") },
                    singleLine = true,
                    visualTransformation = if (notesViewModel.passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester)

                )

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        notesViewModel.signInUser(notesViewModel.emailValue, notesViewModel.passwordValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Sign In", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = Bold
                    ),
                    color = Red200,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .clickable(onClick = {
                             navController.navigate("register_page")
                        })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}