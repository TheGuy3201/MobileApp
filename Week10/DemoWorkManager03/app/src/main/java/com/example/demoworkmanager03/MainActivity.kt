package com.example.demoworkmanager03

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle // Needed for onCreate
import android.util.Log
import androidx.activity.ComponentActivity // Use ComponentActivity for Compose
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.demoworkmanager03.ui.theme.DemoWorkManager03Theme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() { // Inherit from ComponentActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoWorkManager03Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Replace with your actual Composable UI
                    Text(
                        text = "WorkManager Scheduled!",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        setupWorkManager()
    }

    private fun setupWorkManager() {
        val channel =
            NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val notificationWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        Log.i("WorkManager", notificationWorkRequest.toString())

        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }
}

