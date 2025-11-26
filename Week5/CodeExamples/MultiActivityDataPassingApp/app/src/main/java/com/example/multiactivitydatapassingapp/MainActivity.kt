package com.example.multiactivitydatapassingapp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.multiactivitydatapassingapp.ui.theme.MultiActivityDataPassingAppTheme

// Define constants for keys to avoid typos
object IntentKeys {
    const val FIRST_NAME = "FIRST_NAME_KEY"
    const val LAST_NAME = "LAST_NAME_KEY"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiActivityDataPassingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataInputScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataInputScreen() {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Enter Customer Info",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true
        )

        Button(onClick = {
            if (firstName.isNotBlank() && lastName.isNotBlank()) {
                val intent = Intent(context, SecondActivity::class.java).apply {
                    putExtra(IntentKeys.FIRST_NAME, firstName)
                    putExtra(IntentKeys.LAST_NAME, lastName)
                }
                context.startActivity(intent)
            } else {
                // Optionally, show a toast or error message if fields are empty
            }
        }) {
            Text("Send Data to Second Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewMainActivity() {
    MultiActivityDataPassingAppTheme {
        DataInputScreen()
    }
}

