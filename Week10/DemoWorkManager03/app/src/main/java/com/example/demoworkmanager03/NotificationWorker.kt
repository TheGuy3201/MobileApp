package com.example.demoworkmanager03

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setSmallIcon(R.drawable.ic_launcher_background) // Use your app's icon here
            .setContentTitle("Task completed")
            .setContentText("The background task has completed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Perform the background task here,
        // such as displaying a notification
        NotificationManagerCompat.from(applicationContext).notify(1, notification)
        return Result.success()
    }
}
