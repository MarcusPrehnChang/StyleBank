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
            //Row (
             ///   modifier = Modifier
                //    .fillMaxWidth()
            //) {
                //informationOfPicture(
                //    modifier = Modifier
                //)
                //Spacer(modifier = Modifier.width(120.dp))
                //prisSkilt(
                    //modifier = Modifier
                      //  .padding(30.dp)
                //)
            }
        }
    }
//}



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
    if (isOverlayVisible) { // overlay
        Box( //Baggrunden hvid
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column( // Indeholder det store billede
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box( // Det store billede
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(40.dp))
                )
                Row( // Rækken for de to små billeder
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box( // Venstre mindre billede
                        modifier = Modifier
                            .height(180.dp)
                            .width(180.dp)
                            .padding(start = 16.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                            .background(color = Color.Gray, shape = RoundedCornerShape(40.dp))
                    )
                    Box( // Højre mindre billede
                        modifier = Modifier
                            .height(180.dp)
                            .width(180.dp)
                            .padding(start = 0.dp, top = 0.dp, end = 16.dp, bottom = 0.dp)
                            .background(color = Color.Gray, shape = RoundedCornerShape(40.dp))
                    )

                }
                Row( //Rækken for store boksen
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box( // Den sorte box
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                    ) {
                        Text( // teksten
                            text = "STORE",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            ),
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Image( //logo'et for tasken
                            painter = painterResource(id = R.drawable.store),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.CenterEnd)
                                .padding(end = 16.dp)
                        )
                    }
                }
                Column( //Rækken til camo shirt, og tøjmærket
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
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
                        text = "SAUNA",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        color = Color.Gray
                    )
                    Row( // Bank knappen og indeholder krydset (luk overlay)
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
