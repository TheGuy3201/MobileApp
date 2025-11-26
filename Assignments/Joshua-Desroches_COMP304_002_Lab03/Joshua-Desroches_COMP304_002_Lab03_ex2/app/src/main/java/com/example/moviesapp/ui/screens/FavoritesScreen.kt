package com.example.moviesapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.moviesapp.ui.viewmodel.MovieViewModel

// Displays list of favorite movies
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel(),
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(LocalActivity.current)
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Movies") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (favoriteMovies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites yet!", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(favoriteMovies) { movie ->
                     MovieItem(
                        movie = movie,
                        onEdit = { navController.navigate("edit/${movie.id}") },
                        onDelete = { viewModel.delete(movie) },
                        onToggleFavorite = { viewModel.toggleFavorite(movie) }
                    )
                }
            }
        }
    }
}