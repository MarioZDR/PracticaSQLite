package com.example.sqlitepractica233259.presentation

import com.example.sqlitepractica233259.data.Note

sealed interface NotesEvent {
    object SortNotes: NotesEvent

    data class DeleteNote(val note: Note): NotesEvent

    data class SaveNote(
        val title: String,
        val description: String
    ): NotesEvent
}
