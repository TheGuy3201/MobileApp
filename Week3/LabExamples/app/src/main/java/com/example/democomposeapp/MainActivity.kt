package com.example.democomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.democomposeapp.ui.theme.DemoComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            LayoutDemo()
        } // end setContent()
    } //end onCreate()
} //end main activity

@Composable
fun DemoColumn() {
    Column{
        Text(text = "Hello World")
        Text(text = "Welcome to Android Development")
        Text(text = "Greeting Jetpack Compose")
    } //Column
} //DemoColumn

@Composable
fun DemoRow() {
    Row{
        Text(text = "Hello World")
        Text(text = "Welcome to Android")
        Text(text = "Greeting Jetpack Compose")
    } //Column
} //DemoColumn

@Composable
fun LayoutDemo() {
    Column {
        DemoColumn()
        DemoRow()
    }
} //DemoColumn
