package com.example.lazycolumnexample

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Import LazyColumn
import androidx.compose.foundation.lazy.items // Import items extension for LazyColumn
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.lazycolumnexample.ui.theme.LazyColumnExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SimpleLazyColumnScreen()

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLazyColumnScreen() {
    // 1. Prepare your list of data
    val itemsList = (1..100).map { "Item #$it" } // Create a list of 100 strings

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LazyColumn Demo") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        // 2. Use LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding from Scaffold
                .padding(horizontal = 16.dp), // Add some horizontal padding
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between items
        ) {
            // Header (optional)
            item {
                Text(
                    "This is a Header in LazyColumn",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // 3. Display items from the list
            items(itemsList) { individualItemText ->
                // This lambda is called for each item in itemsList
                ListItemView(itemText = individualItemText)
            }

            // Footer (optional)
            item {
                Text(
                    "This is a Footer",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

// Composable function for a single list item's UI
@Composable
fun ListItemView(itemText: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = itemText,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LazyColumnExampleTheme {
        SimpleLazyColumnScreen()
    }
}

