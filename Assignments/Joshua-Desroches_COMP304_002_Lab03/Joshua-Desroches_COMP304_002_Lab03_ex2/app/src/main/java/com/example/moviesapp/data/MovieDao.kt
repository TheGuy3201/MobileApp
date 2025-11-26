package com.example.moviesapp.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Data Access Object for movie-related database operations
@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<Movie>>    // Retrieves all movies as a Flow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie) // Inserts a movie, replacing if conflict occurs

    @Update
    suspend fun update(movie: Movie) // Updates an existing movie

    @Delete
    suspend fun delete(movie: Movie) // Deletes a movie

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Movie>> // Retrieves favorite movies as a Flow

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSampleMovies(movies: List<Movie>) // Inserts sample data, ignoring conflicts
}