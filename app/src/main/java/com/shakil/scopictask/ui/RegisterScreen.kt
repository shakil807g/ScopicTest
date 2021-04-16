package com.shakil.scopictask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shakil.scopictask.R
import com.shakil.scopictask.ui.theme.Grey
import com.shakil.scopictask.ui.theme.Red200
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalComposeUiApi
@InternalCoroutinesApi
@Composable
fun RegisterScreen(navController: NavHostController, notesViewModel: NotesViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {


            Spacer(modifier = Modifier.padding(50.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Sign Up",
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
                    value = notesViewModel.registerEmailValue,
                    onValueChange = { notesViewModel.registerEmailValue = it },
                    label = { Text(text = "Email Address") },
                    placeholder = { Text(text = "Email Address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notesViewModel.registerPasswordValue,
                    onValueChange = { notesViewModel.registerPasswordValue = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            notesViewModel.registerPasswordVisibility = !notesViewModel.registerPasswordVisibility
                        }) {
                            Icon(
                                contentDescription = null,
                                imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
                                tint = if (notesViewModel.registerPasswordVisibility) Red200 else Color.Gray
                            )
                        }
                    },
                    label = { Text("Password") },
                    placeholder = { Text(text = "Password") },
                    singleLine = true,
                    visualTransformation = if (notesViewModel.registerPasswordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester)

                )

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        notesViewModel.createNewUser(notesViewModel.registerEmailValue, notesViewModel.registerPasswordValue)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up", fontSize = 20.sp)
                }

                Row(Modifier.fillMaxWidth().wrapContentSize(Center).padding(vertical = 24.dp)) {
                    Text(
                        text = "Have an Account?  ",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Grey,
                    )

                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Red200,
                        modifier = Modifier
                            .clickable(onClick = {
                                notesViewModel.registerEmailValue  = ""
                                notesViewModel.registerPasswordValue = ""
                                notesViewModel.registerPasswordVisibility = false
                                navController.popBackStack()
                            })
                    )
                }


                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}