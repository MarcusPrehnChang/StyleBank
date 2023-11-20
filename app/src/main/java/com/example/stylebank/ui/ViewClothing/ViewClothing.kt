package com.example.stylebank.ui.ViewClothing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylebank.ui.theme.StyleBankTheme

class ViewClothing : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StyleBankTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}
@Composable
fun WholeScreen() { // Klassen der styre hierchiet for siden
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F5F5))
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        .padding(15.dp)){
        PictureBox()
        Row (modifier = Modifier
            ){
            PictureText("Woodwood", "200kr")
        }
    }
}
@Composable
fun PictureBox() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(23.dp))
            .background(Red)
            .height(650.dp)
            .width(380.dp)
    )
}

@Composable
fun PictureText(tøjMærke: String, pris: String){
    Row(){
    Text(text = "$tøjMærke: $pris")
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview(){
    StyleBankTheme {
        WholeScreen()
    }
}
