package com.example.retrofitexample02

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(WeatherApiService::class.java)

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getCurrentWeather(city, apiKey)
                _weatherData.postValue(response)
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching weather data: ${e.message}")
            }
        }
    }
}
