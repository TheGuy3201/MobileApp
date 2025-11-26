package com.example.lazyrowscoulmns

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazyrowscoulmns.ui.theme.LazyRowsCoulmnsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            PackLazyColumn()
            //PackLazyRow()

        } // end of setContent
    }
} // end of class


@Composable
fun PackLazyColumn() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        items(10) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Item number $it"
            )
        }
    }
}

@Composable
fun PackLazyRow() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(5) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Item number $it"
            )
        }
    }
} // end PackLazyRow()