package com.example.stylebank.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylebank.R
import com.example.stylebank.ui.theme.SwipeScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class SwipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StyleBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeScreen()
                }
            }
        }
    }
}

@Composable
fun SwipeScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {


        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        ){
            val maxWidth = constraints.maxWidth.toFloat()
            val imagePainter = painterResource(id = R.drawable.sb_skjorte)

            Image(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(color = Color.LightGray, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButtonWithText(
                    onClick = { /* handle button click */ },
                    icon = painterResource(id = R.drawable.icon_ild),
                )

                IconButtonWithText(
                    onClick = { /* handle button click */ },
                    icon = painterResource(id = R.drawable.icon_swipe),
                    iconSize = 38.dp
                )

                IconButtonWithText(
                    onClick = { /* handle button click */ },
                    icon = painterResource(id = R.drawable.icon_mb),
                )
            }
        }
    }
}

@Composable
fun IconButtonWithText(
    onClick: () -> Unit,
    icon: Painter,
    iconSize: Dp = 24.dp
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            Color.LightGray,
            contentColor = Color.LightGray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Preview
@Composable
fun SwipeScreenPreview() {
    SwipeScreen()
}