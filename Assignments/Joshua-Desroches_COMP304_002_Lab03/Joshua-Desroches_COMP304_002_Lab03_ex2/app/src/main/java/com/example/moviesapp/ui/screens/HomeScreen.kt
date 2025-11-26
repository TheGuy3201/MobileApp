package com.example.moviesapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviesapp.data.Movie
import com.example.moviesapp.ui.screens.MovieItem
import com.example.moviesapp.ui.viewmodel.MovieViewModel

// Main screen displaying all movies
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel(),
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(LocalActivity.current)
) {
    val movies by viewModel.allMovies.collectAsState()
    println("HomeScreen movies: $movies") // Log to verify data
    val isExpandedScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }
    var searchName by remember { mutableStateOf("") }

    // Add a derived filtered list so searches are efficient and stable across recompositions
    val filteredMovies by remember(searchName, movies) {
        derivedStateOf {
            val q = searchName.trim()
            if (q.isBlank()) movies
            else {
                // match if name contains query (ignore case) or id (as string) contains query
                movies.filter { movie ->
                    movie.title.contains(q, ignoreCase = true) || movie.id.toString().contains(q)
                }
            }
        }
    }

    // If the selected movie is no longer in the filtered results, clear the selection
    LaunchedEffect(searchName, movies) {
        if (selectedMovie != null && !filteredMovies.contains(selectedMovie)) {
            selectedMovie = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie List") },
                actions = {
                    OutlinedTextField(
                        value = searchName,
                        onValueChange = { searchName = it },
                        label = { Text("Search (name or id)") },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(start = 8.dp, end = 8.dp)
                    )
                    IconButton(onClick = { navController.navigate("Home") }) {
                        Icon(Icons.Default.Home, contentDescription = "Display All Movies")
                    }
                    IconButton(onClick = { navController.navigate("favorites") }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                    IconButton(onClick = { navController.navigate("add") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Movie")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isExpandedScreen) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    // use filteredMovies instead of all movies
                    items(filteredMovies) { movie ->
                        MovieItem(
                            movie = movie,
                            onEdit = {
                                navController.navigate("edit/${movie.id}")
                            },
                            onDelete = { viewModel.delete(movie) },
                            onToggleFavorite = { viewModel.toggleFavorite(movie) },
                            modifier = Modifier
                                .clickable { selectedMovie = movie }
                                .background(
                                    if (movie == selectedMovie)
                                        MaterialTheme.colorScheme.secondaryContainer
                                    else Color.Transparent
                                )
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    selectedMovie?.let { movie ->
                        Column {
                            Text(movie.title, style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.height(16.dp))
                            Text("Price of DVD: $${movie.priceOfDVD}", style = MaterialTheme.typography.titleMedium)
                            Text("Genre: ${movie.genre}", style = MaterialTheme.typography.bodyLarge)
                            Text("Release Date: ${movie.dateOfRelease}", style = MaterialTheme.typography.bodyMedium)
                        }
                    } ?: Text(
                        "Select a movie",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                // use filteredMovies here as well
                items(filteredMovies) { movie ->
                    MovieItem(
                        movie = movie,
                        onEdit = { navController.navigate("edit/${movie.id}") },
                        onDelete = { viewModel.delete(movie) },
                        onToggleFavorite = { viewModel.toggleFavorite(movie) },
                        modifier = Modifier
                            .background(
                                if (movie == selectedMovie)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else Color.Transparent
                            )
                    )
                }
            }
        }
    }
}