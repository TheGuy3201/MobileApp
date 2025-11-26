package com.example.textcolumnrowimageexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.textcolumnrowimageexample.ui.theme.TextColumnRowImageExampleTheme
// ...
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContent {
            MessageCard(Message("Josh", "Semester 5", "Game - Programming"))
        }
    }
} //.. end class

//Data class
data class Message(val name: String, val semester: String, val program: String)

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
//        Image(
//            //painter = painterResource(R.drawable.josh),
//            contentDescription = "Contact profile picture",
//            modifier = Modifier
//                // Set image size to 40 dp
//                .size(40.dp)
//                // Clip image to be shaped as a circle
//                .clip(CircleShape)
//        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(text = msg.name, fontSize = 15.sp)
            // Add a vertical space between the author and message texts
            Text(text = msg.semester, fontSize = 15.sp)
            Text(text = msg.program, fontSize = 15.sp)
        }
    }
} // .. end MessageCard
