package com.example.stylebank.ui.theme

import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.stylebank.R
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.viewModel

class SwipeActivity : ComponentActivity()

var list = viewModel.getList("product")
val clothingObserver = object : ObservableListObserver<Clothing> {
    override fun onItemAdded(item: Clothing) {
        if(viewModel.incrementIndex()){
            list = viewModel.getList("product")
        }
    }
}
val add = viewModel.getList("product").registerObserver(clothingObserver)

class Listofclothing : Fragment()

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

@Composable
fun structureOfScreen() { // Holder strukturen for skærmen
    list = viewModel.getList("product")
    println("Clothes in composable: ")
    for (cloth in list) {
        println(cloth.firebaseId)
    }
    var currentIndex by remember { mutableIntStateOf(viewModel.index) }
    var isOverlayVisible by remember { mutableStateOf(false) }
    currentIndex = viewModel.index
    LaunchedEffect(viewModel.index) {
        currentIndex = viewModel.index
    }
    val clothing = list[currentIndex]
    var currentPiece: Clothing = clothing as Clothing
    println("Current piece is : " + currentPiece.firebaseId)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .padding(16.dp, 28.dp, 16.dp, 16.dp),
            ) {
                pictureBox(modifier = Modifier
                    .fillMaxSize(),
                    mainPicture = currentPiece.pictures[0],
                    onPictureClick = { isOverlayVisible = true }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                informationOfPicture(
                    currentPiece.objectName,
                    currentPiece.brandName,
                    modifier = Modifier.weight(1f)
                )
                prisSkilt(
                    modifier = Modifier,
                    currentPiece.price
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dislike {
                    dislike(currentPiece)
                }
                bankButton {
                    like(currentPiece)
                }
            }
            if (isOverlayVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .clickable {
                                isOverlayVisible = false
                            }
                    ) {

                        val imagePainter = painterResource(id = R.drawable.cross)
                        Image(
                            painter = imagePainter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Box( // Store box i overlay
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .fillMaxHeight(0.46f)
                                .padding(start = 30.dp, top = 0.dp, end = 14.dp, bottom = 16.dp)
                                .background(
                                    color = Color.Black,
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
                                        color = Color.Black,
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
                                        color = Color.Black,
                                        shape = RoundedCornerShape(40.dp)
                                    )
                            ) {
                                pictureBox( // Se mig
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
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clickable {}  //currentPiece.link skal laves logik for links

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
                                prisSkilt(
                                    modifier = Modifier,
                                    currentPiece.price
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}





@Composable
fun pictureBox(
    modifier: Modifier = Modifier,
    mainPicture : String,
    onPictureClick: () -> Unit
){
    var isOverlayVisible by remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .background(Color.White, shape = RoundedCornerShape(20.dp))
        .pointerInput(Unit) {
            detectTapGestures {
                onPictureClick()
                isOverlayVisible = true
            }
        }
    ){
        val painter = rememberImagePainter(
            data = mainPicture,
            builder = {
                crossfade(true)
                placeholder(R.drawable.loading3)
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(23.dp))
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}
@Composable
fun bankButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .width(100.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(10.dp) // Adjust padding as needed
                .align(Alignment.CenterStart),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.patchcheck),
                contentDescription = "Dislike",
                modifier = Modifier.size(30.dp) // Enlarge the icon
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}
@Composable
fun dislike(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .width(100.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(10.dp) // Adjust padding as needed
                .align(Alignment.CenterStart),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.patchminus),
                contentDescription = "Dislike",
                modifier = Modifier.size(30.dp) // Enlarge the icon
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}






@Composable
fun informationOfPicture(
    name : String,
    brandName : String,
    modifier: Modifier = Modifier
){
    Column( // Række for tekst - Composable
        modifier = Modifier
            .padding(0.dp, 0.dp)
    ){
        Text(
            text = name,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            color = Color.Black,
        )
        Text(
            text =brandName,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ),
            color = Color.Gray,
        )
    }
}

@Composable
fun prisSkilt(modifier: Modifier = Modifier, price : String){
    Box( //Pris skiltet
        modifier = Modifier
            .height(25.dp)
            .width(65.dp)
            .background(
                color = PriceTagGreen.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = price,
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

fun like(currentPiece : Clothing){
    for (tag in currentPiece.tags) {
        val index = viewModel.user.preferences.indexOfFirst { it.name == tag.name }
        if (index != -1) {
            viewModel.user.preferences[index].value += 1
        } else {
            viewModel.user.preferences.add(tag)
        }
    }
    viewModel.incrementIndex()
    viewModel.addItem("likedItem", currentPiece)
}

fun dislike(currentPiece : Clothing){
    for (tag in currentPiece.tags) {
        val index = viewModel.user.preferences.indexOfFirst { it.name == tag.name }
        if (index != -1) {
            viewModel.user.preferences[index].value -= 1
        } else {
            viewModel.user.preferences.add(tag)
        }
    }
    viewModel.incrementIndex()
}



//@Preview
@Composable
fun SwipeScreenPreview() {

}
//SwipeScreen() }
