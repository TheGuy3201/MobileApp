package com.example.moviesapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

// Reusable dropdown component for genre selection
@Composable
fun GenreDropdown(
    selectedGenre: String,
    onGenreSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val genres = listOf("Action", "Family", "Comedy", "Thriller", "Drama")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedGenre,
            onValueChange = {},
            label = { Text("Genre") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Genre Dropdown"
                )
            },
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genres.forEach { genre ->
                DropdownMenuItem(
                    text = { Text(genre) },
                    onClick = {
                        onGenreSelected(genre)
                        expanded = false
                    }
                )
            }
        }
    }
}