package com.example.joshua_desroches_comp304_002_lab01_ex2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.joshua_desroches_comp304_002_lab01_ex2.ui.theme.JoshuaDesroches_COMP304_002_Lab01_ex2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoshuaDesroches_COMP304_002_Lab01_ex2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductListScreen()
                }
            }
        }
    }
}

// Data class to represent a product
data class Product(
    val name: String,
    val price: Double,
    val manufacturer: String,
    val releaseDate: String,
    val productPicture: Int // Drawable resource ID
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen() {
    // Sample list of products
    val products = remember {
        listOf(
            Product("iPhone 17", 1599.99, "Apple", "2024-09-10", R.drawable.iphone),
            Product("Samsung S25", 919.99, "Samsung", "2024-02-15", R.drawable.samsungs25),
            Product("Google Pixel 10", 1099.99, "Google", "2024-10-01", R.drawable.gpixel),
            Product("Motorola G", 399.99, "Motorola", "2024-05-20", R.drawable.motorola),
            Product("Nothing Phone 3a", 559.99, "Nothing", "2024-03-30", R.drawable.nothing3a)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products, key = { it.name + it.manufacturer }) { product ->
                ProductCard(product = product)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product picture
            Image(
                painter = painterResource(id = product.productPicture),
                contentDescription = "${product.name} picture",
                modifier = Modifier
                    .size(100.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product details
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Manufacturer: ${product.manufacturer}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Release Date: ${product.releaseDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Price: $${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
