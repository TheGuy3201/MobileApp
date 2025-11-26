package com.example.lab4_ex1
import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab4_ex1.ListOfLocations.itemAddress
import com.example.lab4_ex1.ListOfLocations.items
import com.example.lab4_ex1.ListOfLocations.painterFor
import com.example.lab4_ex1.ui.theme.Lab4_ex1Theme

class Joshua : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Lab4_ex1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val category = intent.getStringExtra("category") ?: "Unknown"
                    ItemListScreen(category = category)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(category: String) {
    val context = LocalContext.current

    val items = items(category)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text("Joshua - Explore $category", fontSize = 20.sp)
            },
            navigationIcon = {
                IconButton(onClick = {
                    (context as? ComponentActivity)?.onBackPressedDispatcher?.onBackPressed()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = Color.Blue
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items.size) { index ->
                CategoryCard(
                    item = items[index],
                    onCardClick = { selectedItem ->
                        navigateToMap(selectedItem, context)
                    },
                    category = category
                )
            }
        }
    }
}

private fun navigateToMap(item: String, context: Context) {
    val intent = Intent(context, JoshuaDesroches::class.java).apply {
        putExtra("selectedItem", item) // Pass the selected item to the map activity
    }
    context.startActivity(intent)
}
@Composable
fun CategoryCard(item: String, onCardClick: (String) -> Unit, category: String) {
    val backgroundImage = painterFor(category, item)
    val itemAddress = itemAddress()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { onCardClick(item) }
            .padding(8.dp)
            .shadow(8.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image section (70% of height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                Image(
                    painter = backgroundImage,
                    contentDescription = item,
                    modifier = Modifier
                        .fillMaxSize()
                        .matchParentSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }

            // Text section with white background (30% of height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .background(Color.White),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        //maxLines = 1
                    )
                    Text(
                        text = itemAddress[item] ?: "Address not available",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            color = Color.Gray
                        ),
                        //maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab4_ex1Theme {
        ItemListScreen(category = "Joshua - Destinations")
    }
}
