package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviesapp.ui.viewmodel.MovieViewModel

// Screen for editing an existing movie
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMovieScreen(
    navController: NavController,
    movieId: Int?,
    viewModel: MovieViewModel = viewModel()
) {
    val movies by viewModel.allMovies.collectAsState()
    println("EditMovieScreen: movieId=$movieId, movies=$movies") // Log for debugging

    if (movieId == null) {
        LaunchedEffect(Unit) { navController.popBackStack() }
        return
    }

    val movie = movies.find { it.id == movieId }
    if (movie == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Show loading while waiting for movie
        }
        return
    }

    var editedName by remember { mutableStateOf(movie.title) }
    var editedPrice by remember { mutableStateOf(movie.priceOfDVD.toString()) }
    var editedGenre by remember { mutableStateOf(movie.genre) }
    var editedFavorite by remember { mutableStateOf(movie.isFavorite) }
    var expanded by remember { mutableStateOf(false) } // State for dropdown expansion
    val genres = listOf("Action", "Family", "Comedy", "Thriller", "Drama")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Movie") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(top = 40.dp, start = 16.dp, end = 16.dp)) {

            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it },
                label = { Text("Movie Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = movie.nameOfDirector,
                onValueChange = {},
                readOnly = true,
                label = { Text("Director Name") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = editedPrice,
                onValueChange = { editedPrice = it },
                label = { Text("Price of DVD") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Category Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = editedGenre,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Genre") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Genre Dropdown"
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(genre) },
                            onClick = {
                                editedGenre = genre
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Favorite:")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = editedFavorite, onCheckedChange = { editedFavorite = it })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        viewModel.delete(movie)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete")
                }

                Button(onClick = {
                    val updatedMovie = movie.copy(
                        title = editedName,
                        priceOfDVD = editedPrice.toDoubleOrNull() ?: 0.0,
                        genre = editedGenre,
                        isFavorite = editedFavorite
                    )
                    viewModel.update(updatedMovie)
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save")
                }
            }
        }
    }
}