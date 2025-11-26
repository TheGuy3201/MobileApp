package com.example.navigationexample02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationexample02.ui.theme.NavigationExample02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationExample02Theme {
                NavAppDemo()
            }
        }
    }
}

@Composable
fun NavAppDemo() {
    // NavController is responsible for navigating between screens.
    // rememberNavController() creates and remembers a NavController.
    val navController = rememberNavController()

    // NavHost is a container that displays the current destination.
    NavHost(navController = navController, startDestination = "home") {
        // This defines the "home" screen.
        composable("home") {
            HomeScreen(navController = navController)
        }
        // This defines the "details" screen and its arguments.
        composable(
            "details/{text1}/{text2}",
            arguments = listOf(
                navArgument("text1") { type = NavType.StringType },
                navArgument("text2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieves the arguments from the back stack. [6]
            val arguments = requireNotNull(backStackEntry.arguments)
            val text1 = arguments.getString("text1")
            val text2 = arguments.getString("text2")
            DetailsScreen(text1 = text1, text2 = text2)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    // These states will hold the text from the TextFields.
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TextField for the first input.
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            label = { Text("Enter first text") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // TextField for the second input. [3, 8, 10]
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Enter second text") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Button to navigate to the details screen.
        Button(onClick = {
            // Navigates to the "details" screen with the text from the TextFields as arguments. [11]
            navController.navigate("details/$text1/$text2")
        }) {
            Text("Submit")
        }
    }
}

@Composable
fun DetailsScreen(text1: String?, text2: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("You entered:")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Text 1: ${text1 ?: "No text entered"}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Text 2: ${text2 ?: "No text entered"}")
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    NavigationExample02Theme {
        NavAppDemo()
    }
}
