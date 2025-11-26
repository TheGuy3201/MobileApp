package com.example.demoworkmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.demoworkmanager.ui.theme.DemoWorkManagerTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoWorkManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkManagerDemoApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WorkManagerDemoApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)

    var workId by remember { mutableStateOf<UUID?>(null) }
    val workInfo by workId?.let {
        workManager.getWorkInfoByIdLiveData(it).observeAsState()
    } ?: remember { mutableStateOf(null) } // Handle initial null case

    // For observing one-time work, LaunchedEffect is a good way to start observing
    // when workId is set.
    LaunchedEffect(workId) {
        workId?.let {
            // This is just to ensure observation starts.
            // The observeAsState on LiveData handles the updates.
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                // You can add constraints here if needed
                // .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
                .build()
            workManager.enqueue(workRequest)
            workId = workRequest.id // Store the ID to observe its status
            android.util.Log.d("WorkManagerDemoApp", "Work enqueued with ID: ${workRequest.id}")
        }) {
            Text("Enqueue Simple Work")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Work Status:",
            style = MaterialTheme.typography.headlineSmall
        )

        val statusText = when (workInfo?.state) {
            WorkInfo.State.ENQUEUED -> "Enqueued"
            WorkInfo.State.RUNNING -> "Running"
            WorkInfo.State.SUCCEEDED -> "Succeeded"
            WorkInfo.State.FAILED -> "Failed"
            WorkInfo.State.BLOCKED -> "Blocked"
            WorkInfo.State.CANCELLED -> "Cancelled"
            null -> "No work started yet or ID not found"
        }
        Text(text = statusText, style = MaterialTheme.typography.bodyLarge)

        workInfo?.progress?.let { progress ->
            // If your worker sets progress, you can display it here
            // Text(text = "Progress: ${progress.getInt("PROGRESS_PERCENT", 0)}%")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoWorkManagerTheme {
        WorkManagerDemoApp()
    }
}

/*
Some of the key features of WorkManager are:

WorkManager provides flexible scheduling:
WorkManager allows you to schedule work requests that can be run immediately,
at a later time, or repeatedly at specific intervals.

It also provides you with the flexibility to chain multiple work requests together
to create complex workflows.
WorkManager uses a combination of foreground and background threads
to ensure that work requests are executed efficiently and reliably.

WorkManager is backward compatible:
WorkManager is backward compatible all the
way back to API level 14, which makes it a great option for developers who need to support
older versions of Android. It automatically chooses the best implementation for the current API level,
such as AlarmManager for API levels before 23, JobScheduler for API levels 23 and 24,
and a combination of JobScheduler and Firebase JobDispatcher for API levels 25 and higher.

WorkManager supports constraints:
WorkManager allows you to specify constraints that must be met
before a work request can be executed. Some examples of constraints include network connectivity,
battery charging status, and device idle state. Constraints ensure that your work requests
run at the appropriate time, and they help to conserve battery life and minimize network usage.

WorkManager provides a WorkInfo API:
WorkManager provides a WorkInfo API that allows you to query the status of your work requests.
You can use this API to check whether a work request is running, succeeded, failed, or canceled.
You can also retrieve output data from a completed work request using the WorkInfo API.

WorkManager supports different types of work requests:

WorkManager supports three different types of work requests:

OneTimeWorkRequest: A one-time work request that runs once and then stops.
PeriodicWorkRequest: A work request that runs at specific intervals, such as once a day or once an hour.

CoroutineWorker: A type of worker that uses coroutines to perform asynchronous work.

 */