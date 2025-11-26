package com.example.joshua_desroches_comp304_002_lab02_ex1.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.joshua_desroches_comp304_002_lab02_ex1.R

class EditNotes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_notes)

    }
}

// ViewEditNoteScreen - Allows users to view and edit an existing note
@Composable
fun ViewEditNoteScreen(navController: NavHostController, index: Int) {
    val note = remember { notesList[index] }
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    var date by remember { mutableStateOf(note.date) } // Allow user to edit the date
    var priority by remember { mutableStateOf(note.priority) }
    var email by remember { mutableStateOf(note.email) }

    var expanded by remember { mutableStateOf(false) }
    val priorities = listOf("High", "Medium", "Low")

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

        // Email Input Field (User manually enters the date)
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Priority Dropdown
        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopStart) // Important for positioning
        ) {
            Button(onClick = { expanded = true }) { // Open the dropdown on button click
                Text("Choose the Priority: High, Medium, or Low - Currently Selected: $priority")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Close the dropdown when dismissed
            ) {
                priorities.forEach { courseName ->
                    DropdownMenuItem(
                        text = { Text(courseName) },
                        onClick = {
                            priority = courseName // Update selected course
                            expanded = false         // Close the dropdown
                        }
                    )
                }
            }
        }

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
                notesList[index] = notesList[index].copy(title = title, content = content, email = email, priority = priority, date = date ) // Save the edited note with a new date
                navController.popBackStack() // Navigate back to HomeScreen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}