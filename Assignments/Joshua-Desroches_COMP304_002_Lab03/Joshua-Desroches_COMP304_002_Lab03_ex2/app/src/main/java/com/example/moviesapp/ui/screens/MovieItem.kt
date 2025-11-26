package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.moviesapp.data.Movie

@Composable
fun MovieItem(
    movie: Movie,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Movie info column without clickable modifier
            Column(modifier = Modifier.weight(1f)) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                Text(movie.nameOfDirector, style = MaterialTheme.typography.bodyMedium)
                Text("$${movie.priceOfDVD}", style = MaterialTheme.typography.bodyMedium)
                Text(movie.genre, style = MaterialTheme.typography.bodySmall)
            }

            // Action buttons
            Row {
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.testTag("favorite_button")
                ) {
                    Icon(
                        imageVector = if (movie.isFavorite) Icons.Default.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (movie.isFavorite) Color.Red
                        else MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = {
                        println("Edit clicked for movie ${movie.id}")
                        onEdit()
                    },
                    modifier = Modifier.testTag("edit_button")
                ) {
                    Icon(Icons.Default.Edit, "Edit")
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_button")
                ) {
                    Icon(Icons.Default.Delete, "Delete")
                }
            }
        }
    }
}