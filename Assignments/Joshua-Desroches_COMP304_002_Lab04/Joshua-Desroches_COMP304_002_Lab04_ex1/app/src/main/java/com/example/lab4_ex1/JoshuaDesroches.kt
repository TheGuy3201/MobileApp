package com.example.lab4_ex1

import android.annotation.SuppressLint
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.lab4_ex1.ListOfLocations.itemCoordinates
import com.example.lab4_ex1.ui.theme.Lab4_ex1Theme
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


class JoshuaDesroches : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedItem = intent.getStringExtra("selectedItem") ?: "Unknown Item"

        setContent {
            Lab4_ex1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapScreen(selectedItem)
                }
            }
        }
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
    val mapTypeImg = listOf(
        "Normal",
        "Satellite",
        "Terrain",
        "Hybrid",
        "None"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
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
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = mapTypeImg[mapTypes.indexOf(mapType)].let {
                                when (it) {
                                    "Normal" -> painterResource(id = R.drawable.normal)
                                    "Satellite" -> painterResource(id = R.drawable.satellite)
                                    "Terrain" -> painterResource(id = R.drawable.terrain)
                                    "Hybrid" -> painterResource(id = R.drawable.hybrid)
                                    "None" -> painterResource(id = R.drawable.noimg)
                                    else -> painterResource(id = R.drawable.normal)
                                }
                            },
                            contentDescription = mapType.toString(),
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(selectedItem: String) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val coords = itemCoordinates // Access the coordinates map

    // Default to a fallback location if the item is unknown
    val location = coords[selectedItem] ?: LatLng(0.0, 0.0)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 12f)  // Adjust zoom level as needed
    }

    // Check permission once; we'll enable the map's my-location layer when granted
    val hasLocationPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    // 1. State for the current map type
    var currentMapType by remember {
        mutableStateOf(MapType.SATELLITE) // Initial map type
    }

    // 2. MapProperties updated based on the currentMapType state
    val mapProperties by remember(currentMapType) { // Recompose when currentMapType changes
        mutableStateOf(MapProperties(mapType = currentMapType, isMyLocationEnabled = hasLocationPermission))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Joshua Desroches - Location: $selectedItem") },
                navigationIcon = {
                    IconButton(onClick = { activity?.onBackPressedDispatcher?.onBackPressed() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                            tint = Color.Blue
                        )
                    }
                }

            )
            MapTypeControls(
                currentMapType = currentMapType,
                onMapTypeSelected = { newMapType ->
                    currentMapType = newMapType
                }
            )
        }
    ) { paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = com.google.maps.android.compose.MapUiSettings(
                myLocationButtonEnabled = hasLocationPermission
            )
        ) {

            Marker(
                state = MarkerState(position = location),
                title = selectedItem,
                snippet = "Location of $selectedItem",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
            )

        }
    }
}