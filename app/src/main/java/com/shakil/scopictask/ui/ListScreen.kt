package com.shakil.scopictask.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.shakil.scopictask.domain.Notes
import com.shakil.scopictask.ui.theme.Grey
import com.shakil.scopictask.ui.theme.Red200
import com.shakil.scopictask.viewmodel.NotesViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun ListScreen(navController: NavHostController, notesViewModel: NotesViewModel) {
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
                        .clickable {
                            navController.popBackStack()
                        }
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
                            navController.navigate("profile_page")
                        })
                )

            }

        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                notesViewModel.showAddNoteDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = notesViewModel.isListFromFirebase,
                    onCheckedChange = { notesViewModel.isListFromFirebase = it }
                )
                if (notesViewModel.isListFromFirebase) {
                    FirebaseList(notesViewModel)
                } else {
                    PreferenceList(notesViewModel)
                }
            }
        },

        )
}


@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun FirebaseList(notesViewModel: NotesViewModel) {
    val firebaseNotes: MutableState<List<Notes>> = remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(key1 = Unit, block = {
        val userId = notesViewModel.preferences.userID.first()
        notesViewModel.repo.getList(userId).collect {
            firebaseNotes.value = it
        }
    })

    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(firebaseNotes.value) { index, item ->
            NoteItem(item.text, item.id, notesViewModel) {
                notesViewModel.removeNoteFromFirebase(it)
            }
        }
    }
}

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun PreferenceList(notesViewModel: NotesViewModel) {
    val list = notesViewModel.prefNotes.collectAsState(initial = emptyList())
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        itemsIndexed(list.value) { index, item ->
            NoteItem(item.text, item.id, notesViewModel) {
                notesViewModel.removeNoteFromPref(it)
            }
        }
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun NoteItem(
    note: String,
    id: String,
    notesViewModel: NotesViewModel,
    onSwipe: (id: String) -> Unit
) {

    key(id) {
        val dismissState = rememberDismissState(
            confirmStateChange = {
                if (it == DismissValue.DismissedToStart) {
                    onSwipe(id)
                }
                it != DismissValue.DismissedToStart
            }
        )
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier.padding(vertical = 4.dp),
            directions = setOf(DismissDirection.EndToStart),
            dismissThresholds = { direction ->
                FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
            },
            background = {
                val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        DismissValue.Default -> Color.LightGray
                        DismissValue.DismissedToEnd -> Color.Green
                        DismissValue.DismissedToStart -> Color.Red
                    }
                )
                val alignment = when (direction) {
                    DismissDirection.StartToEnd -> Alignment.CenterStart
                    DismissDirection.EndToStart -> Alignment.CenterEnd
                }
                val icon = when (direction) {
                    DismissDirection.StartToEnd -> Icons.Default.Done
                    DismissDirection.EndToStart -> Icons.Default.Delete
                }
                val scale by animateFloatAsState(
                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                )

                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 20.dp),
                    contentAlignment = alignment
                ) {
                    Icon(
                        icon,
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .scale(scale)
                            .clickable {
                                notesViewModel.removeNoteFromPref(id)
                            }
                    )
                }
            },
            dismissContent = {
                Card(
                    elevation = animateDpAsState(
                        if (dismissState.dismissDirection != null) 4.dp else 0.dp
                    ).value
                ) {
                    ListItem(
                        text = {
                            Text(note, fontWeight = FontWeight.Bold)
                        }
                    )
                }
            }
        )

    }


    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .requiredHeight(1.dp)
            .background(color = Grey.copy(alpha = 0.5f))
    )

}