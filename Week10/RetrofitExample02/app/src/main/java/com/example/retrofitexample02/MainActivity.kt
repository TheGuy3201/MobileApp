package com.example.retrofitexample02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitexample02.ui.theme.RetrofitExample02Theme

class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitExample02Theme {
                // IMPORTANT: Replace with your actual API key
                val apiKey = "ba5146a71df411ab0ee5aefbde2f0c67" // Replace with your key
               // ba5146a71df411ab0ee5aefbde2f0c67
                WeatherScreen(weatherViewModel, apiKey)
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, apiKey: String) {
    val weatherData by viewModel.weatherData.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Fetch weather data on first composition
    LaunchedEffect(Unit) {
        viewModel.fetchWeather("London", apiKey)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            weatherData?.let {
                WeatherInfo(it)
            } ?: run {
                if (errorMessage != null) {
                    Text(text = errorMessage!!)
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun WeatherInfo(weather: WeatherResponse) {
    Text(text = "London", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "${weather.main.temp}°C", fontSize = 48.sp)
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = weather.weather.firstOrNull()?.description ?: "", fontSize = 20.sp)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Humidity: ${weather.main.humidity}%", fontSize = 20.sp)
}