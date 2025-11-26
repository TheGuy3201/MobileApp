package com.example.textcomposebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.textcomposebasics.ui.theme.TextComposeBasicsTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           //Text(text = "Hello World")
            //TextDemo("Android")
            TextStyleExample()

        } //end setContent
    } // end onCreate()
} // end class

@Composable
fun TextDemo(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        color = Color.Blue,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
} // end TextDemo

// Example of Text Style
@Composable
fun TextStyleExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Text with default style")
        //Spacer(modifier = Modifier.height((10.dp))
        Text("Text with cursive font", style = TextStyle(fontFamily = FontFamily.Cursive))
        Text(
            text = "Text with Line Through",
            style = TextStyle(textDecoration = TextDecoration.LineThrough)
        )
        Text(
            text = "Text with Underline",
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
        Text(
            text = "Text with Underline, Line through and Bold",
            style = TextStyle(
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.Underline,
                        TextDecoration.LineThrough
                    )
                ), fontWeight = FontWeight.Bold
            )
        )
    }
}

