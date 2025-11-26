package com.example.retrofitexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.retrofitexample.ui.theme.RetrofitExampleTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostList(
                        posts = viewModel.posts,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PostList(posts: List<Post>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(posts) { post ->
            Text(text = post.title)
        }
    }
}
//===
/*
Key features and functionalities of Retrofit:

Type-safety: Retrofit leverages interfaces and annotations to define
API endpoints and request/response structures, ensuring type-safety and reducing the likelihood of runtime errors.

Declarative API definition: Developers define their
API as an interface with methods annotated for HTTP actions (e.g., @GET, @POST, @PUT, @DELETE) and parameters (e.g., @Query, @Path, @Body). This makes API definitions clear and concise.

Automatic JSON/XML conversion: Retrofit integrates seamlessly
with converter libraries like Gson, Moshi, or Jackson, automatically
serializing Java/Kotlin objects into JSON/XML for requests and deserializing
responses back into objects.

Integration with OkHttp: Retrofit builds upon OkHttp,
a robust HTTP client, for handling the actual network requests,
providing features like connection pooling, GZIP compression, and caching.

Asynchronous requests: Retrofit supports asynchronous network
calls, allowing applications to remain responsive while waiting for
API responses. This is often achieved using Callbacks or Kotlin Coroutines.

Error handling: It provides mechanisms for handling network
errors and API-specific error responses, enabling developers to
implement robust error management
 */