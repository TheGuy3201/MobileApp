package com.example.demoworkermanager02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.demoworkermanager02.ui.theme.DemoWorkerManager02Theme

class MainActivity : ComponentActivity() {

    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val syncStatus = remember { mutableStateOf("Not started") }
            // Add a Spacer before the Button
            Spacer(modifier = Modifier.height(16.dp))
            // Schedule work on button click
            Button(onClick = {
                scheduleSyncUserDataTask()
                syncStatus.value = "Syncing..."
            }) {
                Text(text = "Start Syncing User Data")
            }

            Text(text = syncStatus.value)
        } //end setContent
    }
//} // end class MainActivity

private fun scheduleSyncUserDataTask() {
    val syncWorkRequest = OneTimeWorkRequestBuilder<SyncUserDataWorker>()
        .build()

    // Enqueue the work request
    workManager.enqueue(syncWorkRequest)

    // Optionally, observe the status of the work
    workManager.getWorkInfoByIdLiveData(syncWorkRequest.id).observe(this) { workInfo ->
        if(workInfo != null)
        if (workInfo.state.isFinished) {
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                println("Data sync successful!")
            } else {
                println("Data sync failed!")
            }
        }
    }
}
} // end class MainActivity