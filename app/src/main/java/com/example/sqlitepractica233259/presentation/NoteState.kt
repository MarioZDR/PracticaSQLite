package com.example.sqlitepractica233259.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.sqlitepractica233259.data.Note

data class NoteState(

    val notes: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf("")

)