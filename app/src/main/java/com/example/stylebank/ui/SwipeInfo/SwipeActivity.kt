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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.viewmodel.ProductViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

// Define the ObservableListObserver interface

class SwipeActivity : ComponentActivity() {}


val repository = ClothingRepository()
val viewModel = ProductViewModel(repository)






class Listofclothing : Fragment(){}
val imageID = arrayOf(
    R.drawable.sb_skjorte,
    R.drawable.image3,
    R.drawable.image2,
)


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

@Composable
fun structureOfScreen(){ // Holder strukturen for skærmen
    val (imageIds, setImageIds) = remember { mutableStateOf(imageID.toList()) }
    val clothingObserver = object : ObservableListObserver<Any> {
        override fun onItemAdded(item: Any) {
            println("check")
            val newImageId = R.drawable.image1
            setImageIds(imageIds + listOf(newImageId))
        }
    }
    viewModel.getList("likedItem")?.registerObserver(clothingObserver)
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
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                informationOfPicture(
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(120.dp))
                prisSkilt(
                    modifier = Modifier
                        .padding(30.dp)
                )
            }
        }
    }
}



@Composable
fun pictureBox(
    modifier: Modifier = Modifier,

){
val (imageIds, setImageIds) = remember { mutableStateOf(imageID.toList()) }
    val clothingObserver = object : ObservableListObserver<Any> {
        override fun onItemAdded(item: Any) {

            val newImageId = R.drawable.image1
            setImageIds(imageIds + listOf(newImageId))
        }
    }
    viewModel.getList("product")?.registerObserver(clothingObserver)

    var currentImageIndex by remember { mutableIntStateOf(0) }
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
        .pointerInput(Unit) {
            detectHorizontalDragGestures { change, _ ->
                val offsetX = change.positionChange().x
                if (offsetX > 200f) {
                    //swipe til højre
                    currentImageIndex = (currentImageIndex + 1) % imageIds.size
                    val array = arrayOf("test")
                    val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
                    viewModel.getList("product")?.add(clothing)
                } else if (offsetX < -200f) {
                    //swipe til venstre
                    currentImageIndex = (currentImageIndex - 1 + imageIds.size) % imageIds.size
                    val array = arrayOf("test")
                    val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
                    viewModel.getList("product")?.add(clothing)
                }
            }
        }
    ){
        val imagePainter = painterResource(id = imageIds[currentImageIndex])
        Image( //Billedet i boksen
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            painter = imagePainter,
            contentDescription = null,
        )
    }
}





@Composable
fun informationOfPicture(modifier: Modifier = Modifier){
    Column( // Række for tekst - Composable
        modifier = Modifier
            .padding(30.dp, 0.dp)
    ){
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
            text ="SAUNA",
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
fun prisSkilt(modifier: Modifier = Modifier){
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


//@Preview
@Composable
fun SwipeScreenPreview() {

}
//SwipeScreen() }
