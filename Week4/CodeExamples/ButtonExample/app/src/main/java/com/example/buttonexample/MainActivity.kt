package com.example.buttonexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buttonexample.ui.theme.ButtonExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ButtonExampleTheme {
                IntegerAdditionScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegerAdditionScreen() {
    var number1Input by remember { mutableStateOf("") }
    var number2Input by remember { mutableStateOf("") }
    var sumResult by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun calculateSum() {
        errorMessage = null
        sumResult = null
        val num1 = number1Input.toIntOrNull()
        val num2 = number2Input.toIntOrNull()
        if (num1 == null || num2 == null) {
            errorMessage = "Please enter valid integer values for both numbers."
            return
        }
        sumResult = (num1 + num2).toString()
    }

    fun calculateSub() {
        errorMessage = null
        sumResult = null
        val num1 = number1Input.toIntOrNull()
        val num2 = number2Input.toIntOrNull()
        if (num1 == null || num2 == null) {
            errorMessage = "Please enter valid integer values for both numbers."
            return
        }
        sumResult = (num1 - num2).toString()
    }

    fun calculateMulti() {
        errorMessage = null
        sumResult = null
        val num1 = number1Input.toIntOrNull()
        val num2 = number2Input.toIntOrNull()
        if (num1 == null || num2 == null) {
            errorMessage = "Please enter valid integer values for both numbers."
            return
        }
        sumResult = (num1 * num2).toString()
    }

    fun calculateDiv() {
        errorMessage = null
        sumResult = null
        val num1 = number1Input.toIntOrNull()
        val num2 = number2Input.toIntOrNull()
        if (num1 == null || num2 == null) {
            errorMessage = "Please enter valid integer values for both numbers."
            return
        }
        sumResult = (num1 / num2).toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SIMPLE CALCULATOR") },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Adds space between children
        ) {
            Text(
                text = "Enter two integers to calculate their sum.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            // Input field for the first number
            OutlinedTextField(
                value = number1Input,
                onValueChange = {
                    number1Input = it.filter { char -> char.isDigit() || (it.startsWith('-') && it.count { c -> c == '-' } == 1) } // Allow digits and a leading minus
                    sumResult = null // Clear result when input changes
                    errorMessage = null
                },
                label = { Text("First Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Input field for the second number
            OutlinedTextField(
                value = number2Input,
                onValueChange = {
                    number2Input = it.filter { char -> char.isDigit() || (it.startsWith('-') && it.count { c -> c == '-' } == 1) } // Allow digits and a leading minus
                    sumResult = null // Clear result when input changes
                    errorMessage = null
                },
                label = { Text("Second Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Button to trigger addition
            Button(
                onClick = { calculateSum() },
                modifier = Modifier.fillMaxWidth(0.8f) // Button takes 80% of width
            ) {
                Text("Calculate Sum")
            }

            // Button to trigger subtraction
            Button(
                onClick = { calculateSub() },
                modifier = Modifier.fillMaxWidth(0.8f) // Button takes 80% of width
            ) {
                Text("Calculate Subtraction")
            }

            // Button to trigger multiplication
            Button(
                onClick = { calculateMulti() },
                modifier = Modifier.fillMaxWidth(0.8f) // Button takes 80% of width
            ) {
                Text("Calculate Multiplication")
            }

            // Button to trigger division
            Button(
                onClick = { calculateDiv() },
                modifier = Modifier.fillMaxWidth(0.8f) // Button takes 80% of width
            ) {
                Text("Calculate Division")
            }

            // Display error message if any
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            // Display the result
            sumResult?.let {
                Text(
                    text = "Result: $it",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 28.sp, // Made result text larger
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
