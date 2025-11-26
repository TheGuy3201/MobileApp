package com.example.mapsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapScreen()
        }
    }
}

@Composable
fun MapScreen() {
    val locToronto = LatLng(43.9971, -79.4163)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locToronto, 5f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }

    // 1. State for the current map type
    var currentMapType by remember {
        mutableStateOf(MapType.SATELLITE) // Initial map type
    }

    // 2. MapProperties updated based on the currentMapType state
    val mapProperties by remember(currentMapType) { // Recompose when currentMapType changes
        mutableStateOf(MapProperties(mapType = currentMapType))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .weight(1f) // Make map take available space
                .fillMaxWidth(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings
        )

        // 3. UI to change map type
        MapTypeControls(
            currentMapType = currentMapType,
            onMapTypeSelected = { newMapType ->
                currentMapType = newMapType
            }
        )
    }
}

@Composable
fun MapTypeControls(
    currentMapType: MapType,
    onMapTypeSelected: (MapType) -> Unit,
    modifier: Modifier = Modifier
) {
    val mapTypes = listOf(
        MapType.NORMAL,
        MapType.SATELLITE,
        MapType.TERRAIN,
        MapType.HYBRID,
        MapType.NONE // Useful for a completely custom base layer
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Map Type:")
        Spacer(modifier = Modifier.height(8.dp))
        // Example using Buttons in a Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            mapTypes.forEach { mapType ->
                Button(
                    onClick = { onMapTypeSelected(mapType) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    enabled = mapType != currentMapType // Disable button for current type
                ) {
                    Text(mapType.toString())
                }
            }
        }

        // Alternative using RadioButtons
        // mapTypes.forEach { mapType ->
        //     Row(
        //         verticalAlignment = Alignment.CenterVertically,
        //         modifier = Modifier
        //             .fillMaxWidth()
        //             .padding(vertical = 4.dp)
        //             .clickable { onMapTypeSelected(mapType) }
        //     ) {
        //         RadioButton(
        //             selected = (mapType == currentMapType),
        //             onClick = { onMapTypeSelected(mapType) }
        //         )
        //         Text(
        //             text = mapType.toString(),
        //             modifier = Modifier.padding(start = 8.dp)
        //         )
        //     }
        // }
    }
}