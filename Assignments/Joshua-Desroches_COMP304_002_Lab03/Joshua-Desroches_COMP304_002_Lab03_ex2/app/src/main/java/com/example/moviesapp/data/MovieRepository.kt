package com.example.moviesapp.data
import kotlinx.coroutines.flow.Flow

// Repository class managing data operations between UI and database
class MovieRepository(private val dao: MovieDao) {
    val movies: Flow<List<Movie>> = dao.getAll()        // Stream of all movies
    val favoriteMovies: Flow<List<Movie>> = dao.getFavorites() // Stream of favorite movies

    suspend fun addMovie(movie: Movie) { dao.insert(movie) }    // Adds a new movie
    suspend fun updateMovie(movie: Movie) { dao.update(movie) } // Updates an existing movie
    suspend fun deleteMovie(movie: Movie) { dao.delete(movie) } // Deletes a movie

    // Inserts initial sample movies if needed
    suspend fun insertSampleMovies(movies: List<Movie>) {
        dao.insertSampleMovies(movies)
    }
}