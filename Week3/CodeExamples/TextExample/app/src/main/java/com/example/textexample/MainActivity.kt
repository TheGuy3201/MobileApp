package com.example.textexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
//import androidx.compose.ui.Alignment.Vertical
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.textexample.ui.theme.TextExampleTheme
import androidx.compose.foundation.layout.Arrangement.Vertical
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            TextExampleTheme {
               // Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TextFun(
                        name = "Android",
                        modifier = Modifier.padding(10.dp)
                    )
                // Usage:
                MyCustomText(text = "Hello") // Uses the default empty Modifier
                MyCustomText(text = "World", modifier = Modifier.padding(16.dp).background(Color.Blue)) // Applies custom modifiers

                //}
            }
        }
    }
}

@Composable
fun TextFun(name: String, modifier: Modifier = Modifier) {
   Column( modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center

       ) {
       Text(
           text = "Hello $name!",
           modifier = modifier
       )

       Text(
           text = "Welcome $name!",
           modifier = modifier
       )
   }

}

@Composable
fun MyCustomText(
    text: String,
    modifier: Modifier = Modifier // Default empty Modifier
) {
    Text(
        text = text,
        modifier = modifier // Applying the passed or default modifier
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TextExampleTheme {
        TextFun("Android")
    }
}