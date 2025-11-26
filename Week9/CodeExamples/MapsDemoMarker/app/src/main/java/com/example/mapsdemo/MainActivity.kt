package com.example.mapsdemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.BoxScopeInstance.matchParentSize
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.matchParentSize
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalBottomSheetDefaults.properties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapsdemo.ui.theme.MapsDemoTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapScreen()  //function call
        } // .. setContent
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapScreen() {
    //val locToronto = LatLng(43.9971, -79.4163)
    val locSydney = LatLng(-33.8677, 151.2100)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locSydney, 5f)
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state = MarkerState(position = LatLng(-33.8677, 151.2100)),
            title = "Marker in Sydney"
        )
    } // ..end  GoogleMap()

} //.. end MapScreen()

