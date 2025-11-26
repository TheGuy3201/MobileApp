package com.example.simplemultiactivityapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext // Import
import com.example.simplemultiactivityapp.ui.theme.SimpleMultiActivityAppTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleMultiActivityAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Retrieve data passed from MainActivity (optional)
                    // val userName = intent.getStringExtra("USER_NAME") ?: "Guest"
                    SecondScreen(/*userName = userName*/)
                }
            }
        }
    }
}

@Composable
fun SecondScreen(/*userName: String*/) {
    val currentActivity = LocalContext.current as? Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello from the Second Screen!",
            // text = "Hello, $userName! This is the Second Screen.",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // You could add a button here to explicitly go back or to another activity
         Button(onClick = {
             // To finish this activity and go back to the previous one in the stack

             (currentActivity)?.finish()
         }) {
             Text("Go Back to Main Screen")
         }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewSecondActivity() {
    SimpleMultiActivityAppTheme {
        SecondScreen(/*"Preview User"*/)
    }
}