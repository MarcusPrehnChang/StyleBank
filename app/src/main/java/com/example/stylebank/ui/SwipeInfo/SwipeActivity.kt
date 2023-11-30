package com.example.stylebank.ui.theme

import android.media.Image
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
import coil.request.ImageRequest
import coil.ImageLoader
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
import com.example.stylebank.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter

import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.viewModel
////////
// Define the ObservableListObserver interface

open class SwipeActivity : ComponentActivity() {

}

val list = viewModel.getList("product")
val clothingObserver = object : ObservableListObserver<Any> {
    override fun onItemAdded(item: Any) {
        viewModel.index = viewModel.index + 1
    }
}
val add = viewModel.getList("product")?.registerObserver(clothingObserver)
//val repository = ClothingRepository()
//val viewModel = ProductViewModel(repository)






class Listofclothing : Fragment(){}

/*
val imageID = arrayOf(
    R.drawable.sb_skjorte,
    R.drawable.image3,
    R.drawable.image2,
)*/


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
fun structureOfScreen(){ // Holder strukturen for skærmen

    var currentIndex by remember { mutableIntStateOf(viewModel.index) }
    LaunchedEffect(viewModel.index) {
        currentIndex = viewModel.index
    }
    val clothing = list?.get(currentIndex)

    var currentPiece : Clothing = clothing as Clothing
    Box (modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            pictureBox(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                currentPiece.pictures[0]
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
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
        Box(modifier = Modifier
            .align(Alignment.TopEnd)){
            bankButton {
                viewModel.fetchOne()
                viewModel.addItem("likedItem", currentPiece)
            }
        }
        dislike {
            viewModel.fetchOne()
        }
    }

}



@Composable
fun pictureBox(
    modifier: Modifier = Modifier,
    mainPicture : String

){
    var isOverlayVisible by remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.85f)
        .padding(16.dp, 16.dp, 16.dp, 0.dp) // Måske lav paddingen om med vertical og horizontal
        .background(Color.White, shape = RoundedCornerShape(20.dp))
        .pointerInput(Unit) {
            detectTapGestures {
                isOverlayVisible = true
            }
        }
    ){
        val painter = rememberImagePainter(
            data = mainPicture,
            builder = {
                crossfade(true)
                placeholder(R.drawable.loading)
                //error("error")
            }
        )

        Image(
            painter = painter,
            contentDescription = null, // Set a meaningful content description if needed
            modifier = Modifier.fillMaxSize()
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
                .padding(16.dp)
                .align(Alignment.CenterStart),
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
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
                .padding(16.dp)
                .align(Alignment.CenterStart),
        ) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
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
            .padding(30.dp, 0.dp)
    ){
        Text(
            text = name,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            color = Color.Black
        )
        Text(
            text =brandName,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ),
            color = Color.Gray
        )
    }
}

@Composable
fun prisSkilt(modifier: Modifier = Modifier, price : String){
    Box( //Pris skiltet
        modifier = Modifier
            .padding(0.dp, 0.dp)
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


//@Preview
@Composable
fun SwipeScreenPreview() {

}
//SwipeScreen() }
