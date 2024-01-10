package com.example.stylebank

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.FilterItem
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.ui.theme.StyleBankTheme
import com.example.stylebank.ui.theme.informationOfPicture
import com.example.stylebank.ui.theme.pictureBox
import com.example.stylebank.ui.theme.prisSkilt


class MyBank() : Fragment() {
    //MyBankDisplay()
}
val list = viewModel.getList("likedItem")
val bankObserver = object : ObservableListObserver<Any> {
    override fun onItemAdded(item: Any) {
    }
}
val add = viewModel.getList("likedItem")?.registerObserver(bankObserver)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBankDisplay(clothingList: List<Clothing>) {

    var clickedClothing by remember { mutableStateOf<Clothing?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItems by remember { mutableStateOf(setOf<String>()) }
    val imageIds = remember { mutableStateOf(list) }
    val imageUrls = mutableListOf<String>()
    val imageLinks = mutableListOf<String>()
    val (isOverlayVisible, setIsOverlayVisible) = remember { mutableStateOf(false)}
    if (list != null) {
        for(item in list){
            if(item is Clothing){
                imageUrls.add(item.pictures[0])
                imageLinks.add(item.link)
            }
        }
    }
    val items = listOf(FilterItem("Trøjer"), FilterItem("Bukser"), FilterItem("T-Shirts"))
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))

                items.forEach { item ->
                    val isSelected = selectedItems.contains(item.name)

                    NavigationDrawerItem(
                        label = { Text(text = item.name) },
                        icon = {
                            if (isSelected) {
                                Image(

                                    painter = painterResource(id = R.drawable.fluebenskasse),
                                    contentDescription = "valgt",
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }else{
                                Image(painter = painterResource(id = R.drawable.square),
                                    contentDescription = "ik valgt",
                                    modifier = Modifier.size(25.dp))
                            }
                        },
                        selected = isSelected,
                        onClick = {
                            val isSelected = selectedItems.contains(item.name)

                            selectedItems =  if (isSelected) {
                                viewModel.filter.removeFilterItem(item)
                                Log.d("Removing FilterItems", viewModel.filter.filterItems.joinToString { it.toString() })
                                (selectedItems - item.name).toSet()
                            }  else{
                                viewModel.filter.addFilterItem(item)
                                Log.d("Adding FilterItems", viewModel.filter.filterItems.joinToString { it.toString() })
                                (selectedItems + item.name).toSet()
                            }
                        }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 0.dp, bottom = 16.dp)
                .background(color = Color.White.copy(alpha = 0f), shape = RoundedCornerShape(40.dp))
            ){
                //listen af billeder
                ImageList(
                    clothingList = clothingList,
                    imageUrls = imageUrls,
                    imageLinks = imageLinks
                ) { clickedItem ->
                    clickedClothing = clickedItem
                }
                //Billedet af setting
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ){
                    SettingsButton(drawerState)
                }
                if (clickedClothing != null) {
                    Overlay(
                        clothing = clickedClothing!!,
                        isOverlayVisible = isOverlayVisible,
                        setIsOverlayVisible = setIsOverlayVisible
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsButton(drawerState: DrawerState) {
    // Brug en remember-konstrukt til at holde en tilstand, der kan udløse LaunchedEffect
    var openDrawer by remember { mutableStateOf(false) }

    // Reager på tilstandsændringen for at åbne skuffen
    LaunchedEffect(openDrawer) {
        if (openDrawer) {
            drawerState.open()
            openDrawer = false // Reset tilstanden
        }
    }

    Image(
        painter = painterResource(id = R.drawable.settings),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .clickable { openDrawer = true } // Opdater tilstanden her
    )
}


@Composable
fun ImageList(
    clothingList: List<Clothing>,
    imageUrls: List<String>,
    imageLinks: List<String>,
    onClothClicked: (Clothing) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(clothingList.size) { index ->
            val clothing = clothingList[index]
            BankCloth(
                imageUrl = imageUrls[index],
                link = imageLinks[index],
                clothing = clothing,
                onClothClicked = { onClothClicked(clothing) }
            )
        }
    }
}


@Composable
fun BankCloth(
    imageUrl: String,
    link: String,
    clothing: Clothing,
    onClothClicked: () -> Unit
) {
    val context = LocalContext.current
    var currentPiece: Clothing = clothing

    Box(
        modifier = Modifier
            .height(180.dp)
            .width(180.dp)
            .background(color = Color.Gray.copy(alpha = 0f), shape = RoundedCornerShape(40.dp))
            .clickable { onClothClicked() }
    ) {
        Box(
            modifier = Modifier
                .height(170.dp)
                .width(170.dp)
                .background(
                    color = Color.White.copy(alpha = 0f),
                    shape = RoundedCornerShape(40.dp)
                )
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.loading)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // Only displaying the brandName and price here
                Text(
                    text = currentPiece.brandName,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp)
                )
                Text(
                    text = currentPiece.price,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle()
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingtooPreview() {
    StyleBankTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //GreetingPreview()
            //MyBankDisplay(nav)

        }
    }
}

@Composable
fun Overlay(
    clothing: Clothing,
    isOverlayVisible: Boolean,
    setIsOverlayVisible: (Boolean) -> Unit
) {

    var currentPiece : Clothing = clothing

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
                    setIsOverlayVisible(false)
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


fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if(url.isNotBlank()){
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }else{
            println("some error occured")
        }
    }
}
