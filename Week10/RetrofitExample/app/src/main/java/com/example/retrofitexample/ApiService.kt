package com.example.retrofitexample

//package com.example.retrofitexample.network

import com.example.retrofitexample.Post
import retrofit2.http.GET
//Create a Kotlin interface and use Retrofit annotations
// to define the HTTP requests.
interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
