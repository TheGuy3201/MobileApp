package com.example.retrofitexample02

// This class holds the entire JSON response
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

// This class holds the "main" object from the JSON
data class Main(
    val temp: Double,
    val humidity: Int
)

// This class holds objects inside the "weather" array from the JSON
data class Weather(
    val main: String,
    val description: String
)