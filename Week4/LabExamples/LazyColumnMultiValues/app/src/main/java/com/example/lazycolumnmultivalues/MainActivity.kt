package com.example.lazycolumnmultivalues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Import items extension for LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person // Default person icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazycolumnmultivalues.ui.theme.LazyColumnMultiValuesTheme // Your app's theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnMultiValuesTheme  {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileListScreen()
                }
            }
        }
    }
}

// Data class to represent a profile
data class Profile(
    val id: String,
    val name: String,
    val picturePlaceholder: ImageVector // For this example, using ImageVector
    // In real app, this might be a URL (String) or drawable Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileListScreen() {
    // Sample list of profiles
    val profiles = remember { // Use remember for static list to avoid recreation
        listOf(
            Profile("user101", "Alice Smith", Icons.Filled.Person),
            Profile("user102", "Bob Johnson", Icons.Filled.Person),
            Profile("user103", "Carol Williams", Icons.Filled.Person),
            Profile("user104", "David Brown", Icons.Filled.Person),
            Profile("user105", "Eve Davis", Icons.Filled.Person),
            Profile("user106", "Frank Miller", Icons.Filled.Person),
            Profile("user107", "Grace Wilson", Icons.Filled.Person),
            // Add more profiles as needed
        ).mapIndexed { index, profile ->
            // Simple way to have slightly different "pictures" if you have more icons
            // For now, they are all the same Icon.Filled.Person
            profile.copy(name = "${profile.name} ${index + 1}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profiles") },
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
            items(profiles, key = { it.id }) { profile -> // Use profile.id as a unique key
                ProfileCard(profile = profile)
            }
        }
    }
}

@Composable
fun ProfileCard(profile: Profile) {
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
            // Picture
            Image(
                // Use this if you added a drawable resource named 'ic_person_placeholder'
                // painter = painterResource(id = R.drawable.ic_person_placeholder),

                // For this example, using the ImageVector from the Profile data class
                imageVector = profile.picturePlaceholder,
                contentDescription = "${profile.name}'s picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape), // Make the image circular
                contentScale = ContentScale.Crop // Crop image to fit if not square
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Name and ID
            Column {
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${profile.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
