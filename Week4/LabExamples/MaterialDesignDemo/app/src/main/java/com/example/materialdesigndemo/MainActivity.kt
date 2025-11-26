package com.example.materialdesigndemo;
//
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialDesignDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoScaffold()
                }
            }
        }
    }
}

@Composable
fun MaterialDesignDemoTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFBB86FC),
            secondary = Color(0xFF03DAC5),
            background = Color(0xFF121212),
            surface = Color(0xFF121212),
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC5),
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
        )
    }

    val typography = Typography(
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        )
        // Add other text styles as needed
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}

@Composable
fun DemoScaffold() {
    Scaffold(
        topBar = { PacktCenterAlignedTopBar() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { PacktFloatingActionButton() },
        bottomBar = { PacktBottomNavigationBar() }
    ) { innerPadding ->
        DemoContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacktCenterAlignedTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Material Design 3 Demo") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}

@Composable
fun PacktFloatingActionButton() {
    FloatingActionButton(
        onClick = { /* Handle FAB click */ },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun PacktBottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = false,
            onClick = { /* Handle navigation item click */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { /* Handle navigation item click */ }
        )
    }
}

@Composable
fun DemoContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Material Design 3 Demo", textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialDesignDemoTheme {
        DemoScaffold()
    }
}
