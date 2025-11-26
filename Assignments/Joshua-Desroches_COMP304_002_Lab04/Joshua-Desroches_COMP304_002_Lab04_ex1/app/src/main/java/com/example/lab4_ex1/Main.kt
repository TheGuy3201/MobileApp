package com.example.lab4_ex1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4_ex1.ui.theme.Lab4_ex1Theme

class Main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4_ex1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CategoryList(onCategoryClick = { category ->
                        val intent = Intent(this, Joshua::class.java).apply {
                            putExtra("category", category)
                        }
                        startActivity(intent)
                    })
                }
            }
        }
    }
}

@Composable
fun CategoryList(onCategoryClick: (String) -> Unit) {
    val categories = listOf(
        "Shopping" to R.drawable.harwoodplaza,
        "Restaurants" to R.drawable.restaurant,
        "Parks" to R.drawable.rotarypark,
        "Conservation Areas" to R.drawable.greenwoodconservation
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Heading
        Text(
            text = "Joshua - Durham Destinations",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // Category List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(categories.size) { index ->
                val (category, backgroundImage) = categories[index]
                CategoryCard(category, backgroundImage, onCategoryClick)
            }
        }
    }
}

@Composable
fun CategoryCard(category: String, backgroundImage: Int, onCategoryClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCategoryClick(category) }
            .shadow(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = "$category Background",
                modifier = Modifier
                    .matchParentSize(),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop


            )

            Text(
                text = category,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(15.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 35.sp, // Increase font size
                    fontWeight = FontWeight.Bold, // Make the text bold
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif // Change font family
                ),
                color = Color.Black
            )
        }
    }
}
