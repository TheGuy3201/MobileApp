package com.example.joshuadesroches_comp304_001_test1.Views
//301350618 - Joshua Desroches
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.joshuadesroches_comp304_001_test1.Data.Movie

class DesrochesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieManagementScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieManagementScreen() {
    var movieTitle by remember { mutableStateOf("") }
    var directorName by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("Action") }
    var yearReleased by remember { mutableStateOf("") }
    var lengthInMins by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var movieList by remember { mutableStateOf(listOf<Movie>()) }

    val context = LocalContext.current
    val genres = listOf("Action", "Thriller", "Family", "Classic")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyMovieApp - Joshua Desroches") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Movie Title Field
            OutlinedTextField(
                value = movieTitle,
                onValueChange = { movieTitle = it },
                label = { Text("Movie Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Director Name Field
            OutlinedTextField(
                value = directorName,
                onValueChange = { directorName = it },
                label = { Text("Director Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Genre Dropdown (36dp height for names A-M)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedGenre,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Genre") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                        .height(36.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(genre) },
                            onClick = {
                                selectedGenre = genre
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            // Year Released Field
            OutlinedTextField(
                value = yearReleased,
                onValueChange = { yearReleased = it },
                label = { Text("Year Released") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Length in Minutes Field
            OutlinedTextField(
                value = lengthInMins,
                onValueChange = { lengthInMins = it },
                label = { Text("Length (minutes)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Add Movie Button
            Button(
                onClick = {
                    if (movieTitle.isNotBlank() && directorName.isNotBlank() &&
                        yearReleased.isNotBlank() && lengthInMins.isNotBlank()) {
                        try {
                            val movie = Movie(
                                title = movieTitle,
                                director = directorName,
                                genre = selectedGenre,
                                yearReleased = yearReleased,
                                lengthInMins = lengthInMins.toInt()
                            )
                            movieList = movieList + movie

                            // Show toast with movie details
                            Toast.makeText(
                                context,
                                "Movie Added: ${movie.title} by ${movie.director} (${movie.genre}, ${movie.yearReleased}, ${movie.lengthInMins} mins)",
                                Toast.LENGTH_LONG
                            ).show()

                            // Clear form
                            movieTitle = ""
                            directorName = ""
                            selectedGenre = "Action"
                            yearReleased = ""
                            lengthInMins = ""
                        } catch (_: NumberFormatException) {
                            Toast.makeText(
                                context,
                                "Please enter valid numbers for year and length",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Add Movie")
            }

            // Movies List Header
            if (movieList.isNotEmpty()) {
                Text(
                    text = "Added Movies",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                // Movies List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movieList) { movie ->
                        MovieItem(movie = movie)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Director: ${movie.director}",
                fontSize = 14.sp
            )
            Text(
                text = "Genre: ${movie.genre}",
                fontSize = 14.sp
            )
            Text(
                text = "Year: ${movie.yearReleased} | Length: ${movie.lengthInMins} mins",
                fontSize = 14.sp
            )
        }
    }
}