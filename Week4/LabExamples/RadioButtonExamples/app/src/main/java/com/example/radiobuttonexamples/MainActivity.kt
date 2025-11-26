package com.example.radiobuttonexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.radiobuttonexamples.ui.theme.RadioButtonExamplesTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadioButtonExamplesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SimpleRadioButtonScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleRadioButtonScreen() {
    val radioOptions = listOf("Full Time", "Part Time", "On Line")

    // State to hold the currently selected option.
    // We store the *text* of the selected option.
    var selectedOption by remember { mutableStateOf(radioOptions[0]) } // Default selection

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Radio Button Demo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between elements in Column
        ) {
            Text(
                "Please select one option:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Radio buttons are typically grouped in a Column
            Column {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption), // Check if this option is selected
                                onClick = { selectedOption = text }, // Update state on click
                                role = Role.RadioButton // For accessibility
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with selectable parent
                            // The Row's selectable handles the click.
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                "You selected: $selectedOption",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}



