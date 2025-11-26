package com.example.demoworkmanager

// src/main/java/com/example/demoworkmanager/SimpleWorker.kt

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SimpleWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "SimpleWorker"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Starting work...")
        try {
            // Simulate some background work for 5 seconds
            for (i in 1..10) {
                Log.d(TAG, "Work in progress - Step $i")
                delay(1000)
            }
            Log.d(TAG, "Work finished successfully!")
            return Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Work failed", e)
            return Result.failure()
        }
    }
}