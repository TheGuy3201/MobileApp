package com.example.demoworkermanager02

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SyncUserDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Simulate the data sync task
            delay(3000) // Simulating a network call or data sync operation
            // Returning success after the operation is done
            Result.success()
        } catch (e: Exception) {
            // Handling failure in case of an exception
            Result.failure()
        }
    }
}