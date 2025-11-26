package com.example.moviesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.Movie
import com.example.moviesapp.data.MovieDatabase
import com.example.moviesapp.data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository

    private val _addMovieSuccess = MutableStateFlow(false)
    val addMovieSuccess: StateFlow<Boolean> = _addMovieSuccess.asStateFlow()

    // Form state handling
    data class AddMovieState(
        val id: String = "",
        val title: String = "",
        val nameOfDirector: String = "",
        val priceOfDVD: Double = 0.0,
        val dateOfRelease: String = "",
        val duration: Int = 0,
        val genre: String = "",
        val isFavorite: Boolean = false,
        val errors: List<String> = emptyList()
    )

    private val _addMovieState = MutableStateFlow(AddMovieState())
    val addMovieState: StateFlow<AddMovieState> = _addMovieState.asStateFlow()

    val allMovies: StateFlow<List<Movie>>
    val favoriteMovies: StateFlow<List<Movie>>

    init {
        val dao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(dao)
        allMovies = repository.movies.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
        favoriteMovies = repository.favoriteMovies.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
    }

    fun validateAndAddMovie() {
        val state = _addMovieState.value
        val errors = mutableListOf<String>()

        // ID validation (2 digits, 11-99)
        val id = state.id.toIntOrNull()
        if (id == null || id !in 11..99) errors.add("Invalid ID (11-99)")

        // Title validation
        val name = state.title
        if(name.isBlank())  errors.add("Title must not be empty")

        // Price validation
        val price = state.priceOfDVD
        if (price <= 0) errors.add("Price must be positive")

        // Date validation
        val releaseDate = try {
            LocalDate.parse(state.dateOfRelease)
        } catch (_: Exception) {
            null
        }
        if (releaseDate == null) {
            errors.add("Invalid release date")
        }

        // Duration validation
        val durationMin = state.duration
        if (durationMin <= 0) errors.add("Duration must be over 0")

        // Genre validation
        if (state.genre !in listOf("Action", "Family", "Comedy", "Thriller", "Drama")) {
            errors.add("Select a genre")
        }

        if (errors.isEmpty()) {
            insert(
                Movie(
                    id = id!!,
                    title = state.title,
                    nameOfDirector = state.nameOfDirector,
                    priceOfDVD = state.priceOfDVD,
                    dateOfRelease = state.dateOfRelease,
                    duration = state.duration,
                    genre = state.genre,
                    isFavorite = state.isFavorite

                )
            )
            _addMovieState.update { it.copy(errors = emptyList()) }
            _addMovieSuccess.value = true  // Set success to true
        } else {
            _addMovieState.update { it.copy(errors = errors) }
            _addMovieSuccess.value = false  // Reset success on validation failure
        }
    }

    // Update form fields
    fun updateFormState(
        id: String? = null,
        name: String? = null,
        director: String? = null,
        price: String? = null,
        duration: Int? = null,
        releaseDate: String? = null,
        genre: String? = null,
        isFavorite: Boolean? = null
    ) {
        _addMovieState.update { current ->
            current.copy(
                id = id ?: current.id,
                title = name ?: current.title,
                nameOfDirector = director ?: current.nameOfDirector,
                priceOfDVD = price?.toDoubleOrNull() ?: current.priceOfDVD,
                duration = duration ?: current.duration,
                dateOfRelease = releaseDate ?: current.dateOfRelease,
                genre = genre ?: current.genre,
                isFavorite = isFavorite ?: current.isFavorite

            )
        }
    }

    fun toggleFavorite(movie: Movie) {
        val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)
        viewModelScope.launch {
            repository.updateMovie(updatedMovie)
        }
    }

    fun resetSuccessState() {
        _addMovieSuccess.value = false
    }

    // CRUD operations
    private fun insert(movie: Movie) = viewModelScope.launch { repository.addMovie(movie) }
    fun update(movie: Movie) = viewModelScope.launch { repository.updateMovie(movie) }
    fun delete(movie: Movie) = viewModelScope.launch { repository.deleteMovie(movie) }
}