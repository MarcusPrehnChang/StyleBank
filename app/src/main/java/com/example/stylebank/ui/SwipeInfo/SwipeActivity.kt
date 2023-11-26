package com.example.stylebank.ui.SwipeInfo

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import com.example.stylebank.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.example.stylebank.MyBank
import com.example.stylebank.MyBankDisplay
import com.example.stylebank.ui.theme.BankBlue
import com.example.stylebank.ui.theme.MenubarGray
import com.example.stylebank.ui.theme.PriceTagGreen
import com.example.stylebank.ui.theme.StyleBankTheme

class SwipeActivity(private val navController: NavController) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StyleBankTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeScreen(navController)
                }
            }
        }
    }
}



@Composable
fun SwipeScreen(navController: NavController) {

   /* val SkagenSkjorte = Clothing(
        pictures = arrayOf("https://skagen-clothing.dk/cdn/shop/files/1_19c37989-1486-4ddf-9dbc-19fe56da4316.jpg?v=1685527748&width=1000",
            "https://skagen-clothing.dk/cdn/shop/files/3_ec39fe7b-7568-48c7-8215-dab1acc8ad9e.jpg?v=1685527754&width=1000",
            "https://skagen-clothing.dk/cdn/shop/files/8.jpg?v=1685527833&width=1000"),
        objectName = "Knitted Sweat",
        brandName = "Skagen",
        price = 399,
        link = "https://skagen-clothing.dk/products/striktroje-sort",
        firebaseId = "trHZGNbEcI7imNF0GTfI"
    ) */

    val images = listOf(
        R.drawable.sb_skjorte,
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6
    )

    val imagesInfo = listOf(
        R.drawable.sb_skjorte,
        R.drawable.sb_skjorte,
        R.drawable.sb_skjorte,
    )

    var currentImageIndex by remember { mutableStateOf(0) }
    var isOverlayVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(16.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .pointerInput(Unit) {
                    detectTapGestures {
                        isOverlayVisible = true
                    }
                }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, _ ->
                        val offsetX = change.positionChange().x

                        if (offsetX > 200f) {
                            // Swipe højre
                            currentImageIndex = (currentImageIndex + 1) % images.size
                        } else if (offsetX < -200f) {
                            // Swiped venstre
                            currentImageIndex = (currentImageIndex - 1 + images.size) % images.size
                        }
                    }
                }


        ) {
            val imagePainter = painterResource(id = images[currentImageIndex])

            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                painter = imagePainter,
                contentDescription = null,
            )


        }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(30.dp, 90.dp)
            ) {
                Text(
                    text = "CAMO SHIRT",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Color.Black
                )
                Text(
                    text = "SAUNA",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    ),
                    color = Color.Gray
                )

            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(40.dp, 160.dp)
            ) {
                Text(
                    text = "STYLE",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Color.Gray
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(100.dp, 160.dp)
            ) {
                Text(
                    text = "BANK",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .padding(40.dp, 110.dp)
                    .height(25.dp)
                    .width(65.dp)
                    .align(Alignment.BottomEnd)
                    .background(
                        color = PriceTagGreen.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "118£",
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .height(70.dp)
                    .align(Alignment.BottomCenter)
                    .padding(16.dp, 0.dp)
                    .background(
                        color = MenubarGray,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ),
                contentAlignment = Alignment.Center

            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MenuBarButton(
                        onClick = { /* handle button click */ },
                        icon = painterResource(id = R.drawable.icon_ild),
                    )

                    MenuBarButton(
                        onClick = { /* handle button click */ },
                        icon = painterResource(id = R.drawable.icon_swipe),
                        iconSize = 38.dp
                    )

                    MenuBarButton(
                        onClick = {navController.navigate("MyBankDisplay")},
                        icon = painterResource(id = R.drawable.icon_mb),
                    )
                }
            }
        }
    if (isOverlayVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
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
                        .background(color = Color.Gray, shape = RoundedCornerShape(40.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.skagen1),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(40.dp)),
                        contentScale = ContentScale.Crop
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
                            .background(color = Color.Gray, shape = RoundedCornerShape(40.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.skagen2),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(40.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .width(180.dp)
                            .padding(start = 0.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
                            .background(color = Color.Gray, shape = RoundedCornerShape(40.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.skagen3),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(40.dp)),
                            contentScale = ContentScale.Crop
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
                    ) {
                        Text(
                            text = "STORE",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            ),
                            modifier = Modifier.align(Alignment.Center)
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
                        Text(
                            text = "KNITTED SWEAT",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = Color.Black
                        )
                        Box(
                            modifier = Modifier
                                .height(25.dp)
                                .width(65.dp)
                                .background(
                                    color = PriceTagGreen.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Text(
                                text = "118£",
                                color = Color.Black,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    Text(
                        text = "SKAGEN",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        color = Color.Gray
                    )

                    //val navController = rememberNavController()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .width(250.dp)
                                .padding(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 0.dp)
                                .background(color = BankBlue, shape = RoundedCornerShape(10.dp)),
                        ) {
                            Text(
                                text = "BANK",
                                color = Color.White,
                                style = TextStyle(
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.bank),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp)
                            )
                        }
                        ExitButton(
                            onClick = { /* Handle button click */ },
                            icon = painterResource(id = R.drawable.cross),
                            paddingValue = 10.dp,
                            closeOverlay = { isOverlayVisible = false }
                            )
                        }
                    }
                }
            }
        }
    }




    @Composable
    fun MenuBarButton(
        onClick: () -> Unit,
        icon: Painter,
        iconSize: Dp = 24.dp,
        paddingValue: Dp = 0.dp
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                MenubarGray,
                contentColor = MenubarGray
            ), modifier = Modifier.padding(paddingValue),

        ) {
            Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize)
            )
        }
    }

@Composable
fun ExitButton(
    onClick: () -> Unit,
    icon: Painter,
    iconSize: Dp = 24.dp,
    paddingValue: Dp = 0.dp,
    closeOverlay: () -> Unit
) {
    Button(
        onClick = {
            onClick()
            closeOverlay()
        },
        colors = ButtonDefaults.buttonColors(
            Color.White,
            contentColor = Color.White
        ),
        modifier = Modifier.padding(paddingValue)
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
