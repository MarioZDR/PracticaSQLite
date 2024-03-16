package com.example.sqlitepractica233259


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.sqlitepractica233259.data.NotesDatabase
import com.example.sqlitepractica233259.presentation.AddNoteScreen
import com.example.sqlitepractica233259.presentation.NotesScreen
import com.example.sqlitepractica233259.presentation.NotesViewModel
import com.example.sqlitepractica233259.theme.RoomDatabaseTheme


class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NotesViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDatabaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController= navController, startDestination = "NotesScreen") {
                        composable("NotesScreen") {
                            NotesScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("AddNoteScreen") {
                            var showErrorDialog by remember { mutableStateOf(false) }

                            AddNoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent,
                                showErrorDialog = showErrorDialog,
                                setShowErrorDialog = { showErrorDialog = it})
                    }

                }
            }
        }
    }
}
}