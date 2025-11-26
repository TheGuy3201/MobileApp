package com.example.moviesapp.ui.screens

// LocalActivity.kt
import android.app.Activity
import androidx.compose.runtime.compositionLocalOf

val LocalActivity = compositionLocalOf<Activity> {
    error("LocalActivity not provided")
}
