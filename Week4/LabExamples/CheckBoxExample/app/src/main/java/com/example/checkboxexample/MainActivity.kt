package com.example.checkboxexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.checkboxexample.ui.theme.CheckBoxExampleTheme
//..
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckBoxExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // SimpleCheckboxScreen()
                    MultipleCheckboxScreen()
                }
            }
        }
    }
} // end class MainActivity

@Composable
fun SimpleCheckboxScreen() {
    // 1. State to hold whether the checkbox is checked or not
    var isChecked by remember { mutableStateOf(false) } // Initially unchecked

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 2. Row to align Checkbox and Text horizontally
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Checkbox(
                checked = isChecked, // Current checked state
                onCheckedChange = { newCheckedState -> // Lambda called when the state changes
                    isChecked = newCheckedState // Update the state
                }
                // You can customize colors using CheckboxDefaults.colors()
                // colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Space between checkbox and text
            Text(text = "Subscribe to newsletter")
        }

        // 3. Text to display the current state
        Text(
            text = if (isChecked) {
                "Status: Subscribed!"
            } else {
                "Status: Not Subscribed."
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


// Data class to represent an item with a selectable state
data class SelectableItem(
    val id: Int,
    val name: String,
    var isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipleCheckboxScreen() {
    // Sample list of selectable items (e.g., pizza toppings)
    val initialItems = remember {
        listOf(
            SelectableItem(1, "Pepperoni"),
            SelectableItem(2, "Mushrooms"),
            SelectableItem(3, "Onions"),
            SelectableItem(4, "Sausage"),
            SelectableItem(5, "Olives"),
            SelectableItem(6, "Extra Cheese")
        )
    }

    // State to hold the list of items. We need to recompose when an item's isSelected changes.
    // To do this effectively for a list, we make the list itself a state object,
    // or trigger recomposition manually when an item inside it changes.
    // For simplicity here, we'll recreate the list on change, but for performance with large lists,
    // consider using SnapshotStateList or a ViewModel.
    var items by remember { mutableStateOf(initialItems.toList()) } // Make a mutable copy

    fun onItemCheckedChanged(item: SelectableItem, newCheckedState: Boolean) {
        items = items.map {
            if (it.id == item.id) {
                it.copy(isSelected = newCheckedState)
            } else {
                it
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Your Toppings") },
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
                .padding(16.dp)
        ) {
            Text(
                "Choose your favorite toppings:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display checkboxes in a scrollable list
            LazyColumn(
                modifier = Modifier.weight(1f) // Takes available vertical space
            ) {
                items(items, key = { it.id }) { item -> // Use a key for better performance
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                        // .clickable { onItemCheckedChanged(item, !item.isSelected) } // Optional: make whole row clickable
                    ) {
                        Checkbox(
                            checked = item.isSelected,
                            onCheckedChange = { newCheckedState ->
                                onItemCheckedChanged(item, newCheckedState)
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Display the selected items
            Text(
                "You selected:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val selectedNames = items.filter { it.isSelected }.joinToString { it.name }
            Text(
                text = if (selectedNames.isNotEmpty()) selectedNames else "None",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

