package com.example.retrofitexample

//package com.example.retrofitexample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.Post
import com.example.retrofitexample.RetrofitInstance
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    var posts: List<Post> by mutableStateOf(listOf())
        private set

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                posts = RetrofitInstance.api.getPosts()
            } catch (e: IOException) {
                // Handle network error
            }
        }
    }
}
