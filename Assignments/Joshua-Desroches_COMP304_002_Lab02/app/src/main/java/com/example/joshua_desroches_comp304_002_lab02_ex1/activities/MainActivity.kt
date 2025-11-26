package com.example.joshua_desroches_comp304_002_lab02_ex1.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.joshua_desroches_comp304_002_lab02_ex1.dataclasses.Note
import com.example.joshua_desroches_comp304_002_lab02_ex1.ui.theme.JoshuaDesroches_COMP304_002_Lab02_ex1Theme

// Mutable list to store notes (in-memory storage)
val notesList = mutableStateListOf<Note>()

// MainActivity - Entry point of the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            JoshuaDesroches_COMP304_002_Lab02_ex1Theme {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navController.navigate("create") },
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note",
                                tint = Color.White
                            )
                        }
                    }
                ) { padding ->
                    // Navigation host managing different screens
                    NavHost(navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController, padding) }
                        composable("create") { CreateNoteScreen(navController) }
                        composable("view/{index}") { backStackEntry ->
                            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                            if (index != null && index in notesList.indices) {
                                ViewEditNoteScreen(navController, index)
                            }
                        }
                    }
                }
            }
        }
    }
}