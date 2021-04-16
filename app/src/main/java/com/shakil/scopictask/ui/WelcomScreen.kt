package com.shakil.scopictask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shakil.scopictask.ui.theme.Grey
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun WelcomScreen(navController: NavHostController, notesViewModel: NotesViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Text(
            text = "Welcome",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),

            style = TextStyle(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Hi there! Nice to see you",
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

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "List", fontSize = 20.sp)
        }
    }

}