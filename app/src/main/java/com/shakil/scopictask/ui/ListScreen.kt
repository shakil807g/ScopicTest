package com.shakil.scopictask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.shakil.scopictask.ui.theme.Red200
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import androidx.compose.runtime.*

@InternalCoroutinesApi
@Composable
fun ListScreen(navController: NavHostController, notesViewModel: NotesViewModel) {
    val scaffoldState = rememberScaffoldState()
    var checkedState by remember { mutableStateOf(true) }
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
                    text = "List",
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
                )


                Text(
                    text = "Profile",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Red200,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable(onClick = {
                            navController.navigate("register_page")
                        })
                )

            }

        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = {
                notesViewModel.showAddNoteDialog = true
        }){
            Icon(Icons.Default.Add, contentDescription = null)
        } },
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(
                    checked = checkedState,
                    onCheckedChange = { checkedState = it }
                )
                if(checkedState) {
                    FirebaseList(notesViewModel)
                } else {
                    PreferenceList(notesViewModel)
                }
                }
            },

    )
}


@InternalCoroutinesApi
@Composable
fun FirebaseList(notesViewModel: NotesViewModel) {
    LazyColumn(
        Modifier
            .fillMaxSize()) {
         itemsIndexed(notesViewModel.firebaseNotes.value) { index,item ->
            Text(text = item.text,Modifier.padding())
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(1.dp)
                .background(color = MaterialTheme.colors.surface))
        }
    }
}

@InternalCoroutinesApi
@Composable
fun PreferenceList(notesViewModel: NotesViewModel) {
    val list = notesViewModel.prefNotes.collectAsState(initial = emptyList())
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.Red)) {
        itemsIndexed(list.value) { index,item ->
            Text(text = item.text,Modifier.padding())
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(1.dp)
                .background(color = MaterialTheme.colors.surface))
        }
    }
}