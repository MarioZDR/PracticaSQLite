package com.example.sqlitepractica233259.presentation


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit,
    showErrorDialog: Boolean,
    setShowErrorDialog:(Boolean)-> Unit
) {
    fun isValidInput(input: String): Boolean {
        return input.isNotBlank()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (isValidInput(state.title.value) && isValidInput(state.description.value)) {
                    onEvent(
                        NotesEvent.SaveNote(
                            title = state.title.value,
                            description = state.description.value
                        )
                    )
                    navController.popBackStack()
                } else {
                    setShowErrorDialog(true)
                }
            }) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.title.value,
                onValueChange = {
                    state.title.value = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = {
                    Text(text = "Title")
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.description.value,
                onValueChange = {
                    state.description.value = it
                },
                placeholder = {
                    Text(text = "Description")
                }
            )
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { setShowErrorDialog(false) },
                title = { Text(text = "Error") },
                text = { Text(text = "Please, fill in all the fields.") },
                confirmButton = {
                    Button(
                        onClick = { setShowErrorDialog(false) }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

    }
}