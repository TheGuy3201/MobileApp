package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.ui.navigation.AppNavigation
import com.example.moviesapp.ui.screens.LocalActivity
import com.example.moviesapp.ui.theme.Moviesapp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                Moviesapp {
                    // Get window size class for responsive layouts
                    val windowSizeClass = calculateWindowSizeClass(this@MainActivity)
                    // Create navigation controller
                    val navController = rememberNavController()

                    // Main app content
                    AppContent(
                        windowSizeClass = windowSizeClass,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun AppContent(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController
) {
    MaterialTheme {
        AppNavigation(
            windowSizeClass = windowSizeClass,
            navController = navController
        )
    }
}