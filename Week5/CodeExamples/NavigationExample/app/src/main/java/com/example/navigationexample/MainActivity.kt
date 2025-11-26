package com.example.navigationexample
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
import com.example.navigationexample.ui.theme.NavigationExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationExampleTheme {
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
    NavigationExampleTheme {
        Greeting("Android")
    }
}

 */

//package com.example.navigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.ui.theme.NavigationExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationExampleTheme {
                // The Scaffold provides a layout structure.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // We call our main navigation composable here.
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 * This composable is the heart of our navigation. It creates the NavController
 * and defines the navigation graph using NavHost.
 */
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    // Creates and remembers the NavController across recompositions.
    val navController = rememberNavController()

    // NavHost is the container for all your navigation destinations.
    NavHost(
        navController = navController,
        startDestination = "home", // The route for the first screen to be shown.
        modifier = modifier
    ) {
        // Defines the "home" screen destination.
        composable(route = "home") {
            HomeScreen(navController = navController)
        }

        // Defines the "details" screen destination.
        composable(route = "details") {
            DetailsScreen(navController = navController)
        }
    }
}

/**
 * Represents the first screen of our app.
 * @param navController The controller used to navigate to other screens.
 */
@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Button(onClick = {
            // This is the action to navigate to the "details" route.
            navController.navigate("details")
        }) {
            Text("Go to Details")
        }
    }
}

/**
 * Represents the second screen of our app.
 * @param navController The controller used to navigate back.
 */
@Composable
fun DetailsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Details Screen", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Button(onClick = {
            // This action navigates back to the previous screen in the stack.
            navController.popBackStack()
        }) {
            Text("Go Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NavigationExampleTheme {
        // For preview, we pass a new NavController instance.
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    NavigationExampleTheme {
        DetailsScreen(navController = rememberNavController())
    }
}
