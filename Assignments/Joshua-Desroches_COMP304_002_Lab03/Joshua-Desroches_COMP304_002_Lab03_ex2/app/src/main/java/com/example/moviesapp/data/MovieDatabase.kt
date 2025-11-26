package com.example.moviesapp.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Room database containing the movies table
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao // Provides access to MovieDao

    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null

        // Singleton pattern to get or create the database instance
        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Prepopulate database with sample data on first creation
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).movieDao().insertSampleMovies(
                                    listOf(
                                        Movie(
                                            11,
                                            "Avengers: Endgame",
                                            "Anthony and Joe Russo",
                                            19.99,
                                            "2019-04-26",
                                            181,
                                            "Action",
                                            true

                                        ),
                                        Movie(
                                            12,
                                            "The Godfather",
                                            "Francis Ford Coppola",
                                            14.99,
                                            "1972-03-24",
                                            175,
                                            "Drama",
                                            false
                                        ),
                                        Movie(
                                            13,
                                            "Inception",
                                            "Christopher Nolan",
                                            17.99,
                                            "2010-07-16",
                                            148,
                                            "Thriller",
                                            true
                                        )
                                    )
                                )
                            }
                        }
                    })
                    .build()
                    .also { Instance = it }
            }
        }
    }
}