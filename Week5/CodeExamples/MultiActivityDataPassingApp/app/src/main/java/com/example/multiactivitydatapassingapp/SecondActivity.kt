package com.example.multiactivitydatapassingapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext // Import LocalContext
import com.example.multiactivitydatapassingapp.ui.theme.MultiActivityDataPassingAppTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the data from the Intent
        val receivedFirstName = intent.getStringExtra(IntentKeys.FIRST_NAME) ?: "N/A"
        val receivedLastName = intent.getStringExtra(IntentKeys.LAST_NAME) ?: "N/A"

        setContent {
            MultiActivityDataPassingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayDataScreen(firstName = receivedFirstName, lastName = receivedLastName)
                }
            }
        }
    }
}

@Composable
fun DisplayDataScreen(firstName: String, lastName: String) {
    val currentActivity = LocalContext.current as? Activity // Get the activity context
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Data from Main Activity",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            "First Name: $firstName",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Last Name: $lastName",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = {
            // To finish this activity and go back to the previous one in the stack
            currentActivity?.finish()
        }) {
            Text("Go Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewSecondActivity() {
    MultiActivityDataPassingAppTheme {
        DisplayDataScreen(firstName = "John", lastName = "Doe")
    }
}

