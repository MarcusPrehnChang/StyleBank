/*package com.example.stylebank.ui.SwipeInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stylebank.R
import com.example.stylebank.ui.theme.informationOfPicture
import com.example.stylebank.ui.theme.pictureBox
import com.example.stylebank.ui.theme.prisSkilt

class hej {
    @Composable
    fun ExitButton(
        onClick: () -> Unit,
        icon: Painter,
        iconSize: Dp = 24.dp,
        paddingValue: Dp = 0.dp,
}


var isOverlayVisible by remember { mutableStateOf(false) }

pictureBox(
modifier = Modifier
.align(Alignment.CenterHorizontally),
currentPiece.pictures[0],
onPictureClick = { isOverlayVisible = true }


if (isOverlayVisible) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clickable {
                    isOverlayVisible = false
                }
        ) {

            val imagePainter = painterResource(id = R.drawable.cross)

            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0f),
                        shape = RoundedCornerShape(40.dp)
                    )
            ) {
                pictureBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    currentPiece.pictures[0],
                    onPictureClick = {}
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .width(180.dp)
                        .padding(start = 16.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0f),
                            shape = RoundedCornerShape(40.dp)
                        )
                ) {
                    pictureBox(
                        modifier = Modifier
                            .fillMaxSize(),
                        currentPiece.pictures[1],
                        onPictureClick = {}
                    )
                }
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .width(180.dp)
                        .padding(start = 0.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0f),
                            shape = RoundedCornerShape(40.dp)
                        )
                ) {
                    pictureBox(
                        modifier = Modifier
                            .fillMaxSize(),
                        currentPiece.pictures[2],
                        onPictureClick = {}
                    )
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                        .clickable { currentPiece.link }

                ) {
                    Text(
                        text = "STORE",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.store),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    informationOfPicture(
                        currentPiece.objectName,
                        currentPiece.brandName,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    prisSkilt(
                        modifier = Modifier
                            .padding(30.dp),
                        currentPiece.price
                    )
                }
            }
        }
    }
}
}

var isOverlayVisible by remember { mutableStateOf(false) }


Box(modifier = Modifier
.fillMaxWidth()
.fillMaxHeight(0.85f)
.padding(16.dp, 16.dp, 16.dp, 0.dp) // Måske lav paddingen om med vertical og horizontal
.background(Color.White, shape = RoundedCornerShape(20.dp))
.pointerInput(Unit) {
    detectTapGestures {
        onPictureClick()
    }
}
) {

}


       *///