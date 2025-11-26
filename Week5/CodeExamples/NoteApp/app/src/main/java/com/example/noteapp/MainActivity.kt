package com.example.noteapp
/*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.noteapp.ui.theme.NoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteAppTheme {
        Greeting("Android")
    }
}

 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.ui.theme.NoteAppTheme

// Data Model representing a Note
data class Note(var title: String, var content: String, var date: String)

// Mutable list to store notes (in-memory storage)
val notesList = mutableStateListOf<Note>()

// MainActivity - Entry point of the application
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NoteAppTheme  {
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

// Home Screen - Displays the list of saved notes
@Composable
fun HomeScreen(navController: NavHostController, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NOTE APP",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // List of saved notes using LazyColumn
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(notesList.indices.toList()) { index ->
                val note = notesList[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { navController.navigate("view/$index") }, // Navigate to note view/edit
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (note.content.length > 50) note.content.take(50) + "..." else note.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Date: ${note.date}",
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

// CreateNoteScreen - Allows users to create a new note with a manually entered date
@Composable
fun CreateNoteScreen(navController: NavHostController) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") } // User will enter the date manually

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "New Note",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Title Input Field
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        // Content Input Field
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )

        // Date Input Field (User manually enters the date)
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button - Saves the note and navigates back
        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank() && date.isNotBlank()) {
                    notesList.add(Note(title, content, date)) // Save note with manually entered date
                    navController.popBackStack() // Navigate back to HomeScreen
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Note")
        }
    }
}

// ViewEditNoteScreen - Allows users to view and edit an existing note
@Composable
fun ViewEditNoteScreen(navController: NavHostController, index: Int) {
    val note = remember { notesList[index] }
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    var date by remember { mutableStateOf(note.date) } // Allow user to edit the date

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Edit Note",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Title Input Field
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        // Content Input Field
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )

        // Date Input Field (User manually enters the date)
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button - Updates the note and navigates back
        Button(
            onClick = {
                notesList[index] = notesList[index].copy(title = title, content = content, date = date) // Save the edited note with a new date
                navController.popBackStack() // Navigate back to HomeScreen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
