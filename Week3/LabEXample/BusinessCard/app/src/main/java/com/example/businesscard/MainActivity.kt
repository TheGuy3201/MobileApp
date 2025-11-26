package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCard(Information(
                name = "Joshua Desro",
                title = "Game - Programming",
                email = "jdesro14@my.centennialcollege.ca",
                phone = "123-456-7890"))
        }
    }
}

data class Information(val name: String,
                       val title: String,
                       val email: String,
                       val phone: String)


@Composable
fun BusinessCard(info: Information)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
//        Image(
//            painter = painterResource(R.drawable.josh),
//            contentDescription = "Contact profile picture",
//            modifier = Modifier
//                // Set image size to 40 dp
//                .size(100.dp)
//                // Clip image to be shaped as a circle
//                .clip(CircleShape)
//        )
        BusinessCardNorth(Information(
            name = "Joshua Desro",
            title = "Game - Programming",
            email = "jdesro14@my.centennialcollege.ca",
            phone = "123-456-7890"))

        BusinessCardSouth(Information(
            name = "Joshua Desro",
            title = "Game - Programming",
            email = "jdesro14@my.centennialcollege.ca",
            phone = "123-456-7890"))

    }
}

@Composable
fun BusinessCardNorth(info: Information)
{
    Image(
        painter = painterResource(R.drawable.josh),
        contentDescription = "Contact profile picture",
        modifier = Modifier
            // Set image size to 40 dp
            .size(240.dp)
            // Clip image to be shaped as a circle
            .clip(CircleShape)
    )

    Text(text = info.name, fontSize = 40.sp, fontWeight = FontWeight.Bold)
    Text(text = info.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
    Spacer(modifier = Modifier.height(200.dp))
}

@Composable
fun BusinessCardSouth(info: Information)
{
    Row()
    {
        Icon(painter = painterResource(R.drawable.email),
            contentDescription = "Email Icon",
            tint = Color.Red,
            modifier = Modifier.size(40.dp))
        Spacer(
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(text = info.email, fontSize = 20.sp)
    }

    Row()
    {
        Icon(painter = painterResource(R.drawable.phone),
            contentDescription = "Phone Icon",
            tint = Color.Red,
            modifier = Modifier.size(40.dp))

        Spacer(
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(text = info.phone, fontSize = 20.sp)
    }
}

@Composable
fun SimpleColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Android", fontSize = 20.sp)
        Text(text = "Kotlin", fontSize = 20.sp)
        Text(text = "Compose", fontSize = 20.sp)
    }
}

@Composable
fun SimpleRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Android", fontSize = 20.sp)
        Text(text = "Kotlin", fontSize = 20.sp)
        Text(text = "Compose", fontSize = 20.sp)
    }
}

@Composable
fun SimpleBox() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(80.dp)
        )
        Text(text = "9", color = Color.Black, fontSize = 24.sp, textAlign = TextAlign.Center)
    }
}