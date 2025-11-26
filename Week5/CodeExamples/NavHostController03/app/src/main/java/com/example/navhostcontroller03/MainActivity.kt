package com.example.navhostcontroller03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.navhostcontroller03.ui.theme.NavHostController03Theme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() is not needed for this basic layout
        setContent {
            NavHostController03Theme {
                // The main entry point for our navigation app
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    // NavController manages navigation between composables (screens).
    val navController = rememberNavController()

    // NavHost is the container that displays the current navigation destination.
    NavHost(navController = navController, startDestination = "home_screen") {
        // Defines the "home" screen of the app.
        composable(route = "home_screen") {
            HomeScreen(navController = navController)
        }
        // Defines the "details" screen and specifies its arguments.
        // It expects two string arguments: text1 and text2.
        composable(
            route = "details_screen/{text1}/{text2}",
            arguments = listOf(
                navArgument("text1") { type = NavType.StringType },
                navArgument("text2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieves the arguments passed from the HomeScreen.
            val arguments = requireNotNull(backStackEntry.arguments)
            val text1 = arguments.getString("text1")
            val text2 = arguments.getString("text2")

            // We create a list to use with LazyColumn.
            val textList = listOf(
                "First Input: ${text1 ?: "No text"}",
                "Second Input: ${text2 ?: "No text"}"
            )

            DetailsScreen(texts = textList)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    // 'remember' and 'mutableStateOf' create a state that holds the text
    // from the TextFields. The UI will update when this state changes.
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
        // TextField for the second input.
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Enter second text") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            // It's good practice to URL-encode arguments to handle special characters.
            val encodedText1 = URLEncoder.encode(text1, StandardCharsets.UTF_8.toString())
            val encodedText2 = URLEncoder.encode(text2, StandardCharsets.UTF_8.toString())
            // Navigates to the "details" screen, passing the text from the
            // TextFields as arguments in the route.
            navController.navigate("details_screen/$encodedText1/$encodedText2")
        }) {
            Text("Show on Next Screen")
        }
    }
}

@Composable
fun DetailsScreen(texts: List<String>) {
    // LazyColumn is an efficient way to display a list of items,
    // as it only composes and lays out the items currently visible on screen.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // items() is a LazyColumn DSL function to add a list of items.
        items(texts) { text ->
            Text(text = text, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NavHostController03Theme {
        // We pass a dummy NavController for the preview to work.
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    NavHostController03Theme {
        DetailsScreen(texts = listOf("First Input: Hello", "Second Input: World"))
    }
}
