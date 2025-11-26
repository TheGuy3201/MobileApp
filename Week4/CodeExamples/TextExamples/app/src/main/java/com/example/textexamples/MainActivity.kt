package com.example.textexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textexamples.ui.theme.TextExamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            TextExamplesTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                UserInputScreen()
            } // end of theme
        } // end of setContent
    }
}


////////////...
/*
package com.example.userinputapp // Replace with your package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userinputapp.ui.theme.UserInputAppTheme // Your app's theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserInputAppTheme { // Apply your app's theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserInputScreen()
                }
            }
        }
    }
}
*/
@OptIn(ExperimentalMaterial3Api::class) // For TopAppBar and other Material 3 components
@Composable
fun UserInputScreen() {
    // 1. State to hold the user's input
    var nameInput by remember { mutableStateOf("") }
    var lNameInput by remember { mutableStateOf("") }
    var addressInput by remember { mutableStateOf("") }
    var greetingMessage by remember { mutableStateOf("Please enter your first name below.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple User Input") },
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
                .padding(innerPadding) // Apply padding from Scaffold
                .padding(16.dp),      // Additional padding for content
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Align content to the top
        ) {
            // 2. Text to display the greeting or instruction
            Text(
                text = greetingMessage,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 3. TextField for first name input
            OutlinedTextField(
                value = nameInput,
                onValueChange = { newName ->
                    nameInput = newName // Update the state with what the user types
                },
                label = { Text("Your First Name") },
                singleLine = true, // Ensure it's a single line input
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. TextField for last name input
            OutlinedTextField(
                value = lNameInput,
                onValueChange = { newLName ->
                    lNameInput = newLName // Update the state with what the user types
                },
                label = { Text("Your Last Name") },
                singleLine = true, // Ensure it's a single line input
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            //5. Your address input
            OutlinedTextField(
                value = addressInput,
                onValueChange = { newAddress ->
                    addressInput = newAddress // Update the state with what the user types
                },
                label = { Text("Your Address") },
                singleLine = true, // Ensure it's a single line input
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))



            // 4. Button to process the input (optional, but good for explicit action)
            Button(
                onClick = {
                    if (nameInput.isNotBlank() && lNameInput.isNotBlank() && addressInput.isNotBlank()) {
                        greetingMessage = "Hello, $nameInput $lNameInput at $addressInput!"
                    } else {
                        greetingMessage = "Please fill each field below."
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f) // Make button not take full width
            ) {
                Text("Say Hello")
            }

            // You could also have the greeting message update live without a button:
            // if (nameInput.isNotBlank()) {
            //    Text("Hello, $nameInput!", fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp))
            // }
        } // end column
    } // end Inner padding
} // end fun UserInputScreen

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UserInputAppTheme {
        UserInputScreen()
    }
}
*/