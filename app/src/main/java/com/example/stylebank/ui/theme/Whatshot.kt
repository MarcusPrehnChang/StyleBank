package com.example.stylebank.ui.theme

import com.example.stylebank.R


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle


class Whatshot : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StyleBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WholeScreen()
                }
            }
        }
    }
}

@Composable
fun WholeScreen() { // Laver hierchiet for hele siden
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF6F5F5))){
        Column {
            Headertext()
            TitlePictureRow()
        }
    }

}
@Composable
fun Headertext() { // Laver hovedetitel (Header)
    val header = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("STYLE")
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.Thin)) {
            append("BANK")
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color.LightGray)
            .padding(20.dp)
    ) {
        Text(
            text = header,
            fontSize = 28.sp,
        )
        whatsHot()
    }
}
@Composable
fun whatsHot() { // Undertitel (Subheader)
    Row() {
        Text(text = "WHAT'S H",
            style = TextStyle(
                fontSize = 20.sp,
                //fontStyle = FontStyle.Roboto
            ),
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(id = R.drawable.fire__1_),
            contentDescription = null,
            modifier = Modifier.size(23.dp)
        )
        Text(text = "T",
            style = TextStyle(
                fontSize = 20.sp
            ),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable // Laver rækkerne med titel + billeder
fun TitlePictureRow() {
    val modifier = Modifier
        .padding(10.dp)
    Column {
        Text(text = "WoodWood", modifier = Modifier.padding(start = 10.dp))
        LazyRow { // Laver rækken af billeder
            items(count = 4) { index ->
                imageboxs(modifier.size(140.dp))
            }
        }
    }
}
@Composable // Viser billeder
fun imageboxs(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ch_25_phantom_1_),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(23.dp))
            .background(Color.White)
    )
}

@Composable // liste af billeder
fun listPictures(){

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WhatsHotPreview() {
    StyleBankTheme {
        WholeScreen()
    }

}


