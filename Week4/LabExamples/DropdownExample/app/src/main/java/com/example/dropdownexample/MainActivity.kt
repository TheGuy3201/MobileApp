package com.example.dropdownexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dropdownexample.ui.theme.DropdownExampleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropdownExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SimpleDropdownScreen()
                }
            }
        }
    }
}

@Composable
fun SimpleDropdownScreen() {
    val courses = listOf("Java", "Kotlin", "Python", "C++", "C#")
    var expanded by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf("No course selected yet") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Selected: $selectedCourse",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Box is used to anchor the DropdownMenu to the Button
        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopStart) // Important for positioning
        ) {
            Button(onClick = { expanded = true }) { // Open the dropdown on button click
                Text("Select a Course")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false } // Close the dropdown when dismissed
            ) {
                courses.forEach { courseName ->
                    DropdownMenuItem(
                        text = { Text(courseName) },
                        onClick = {
                            selectedCourse = courseName // Update selected course
                            expanded = false         // Close the dropdown
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DropdownExampleTheme {
        SimpleDropdownScreen()
    }
}



