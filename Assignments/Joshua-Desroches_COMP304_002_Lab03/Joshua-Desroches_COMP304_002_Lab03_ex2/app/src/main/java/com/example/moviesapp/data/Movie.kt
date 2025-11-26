package com.example.moviesapp.data
import androidx.room.PrimaryKey
import androidx.room.Entity

// Represents a movie entity in the Room database
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val nameOfDirector: String,
    val priceOfDVD: Double,
    val dateOfRelease: String,
    val duration: Int,
    val genre: String,
    val isFavorite: Boolean
)