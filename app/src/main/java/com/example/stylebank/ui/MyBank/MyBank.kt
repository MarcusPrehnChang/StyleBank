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
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.FilterItem
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.ui.theme.informationOfPicture
import com.example.stylebank.ui.theme.pictureBox
import com.example.stylebank.ui.theme.prisSkilt


class MyBank() : Fragment() {
    //MyBankDisplay()
}
val list = viewModel.getList("likedItem")
val bankObserver = object : ObservableListObserver<Clothing> {
    override fun onItemAdded(item: Clothing) {
    }
}
val add = viewModel.getList("likedItem").registerObserver(bankObserver)

fun filterClothingByTagNames(clothingList: List<Clothing>, selectedItems: List<String>): List<Clothing> {
    // If the tagNames array is empty, return the entire list
    if (selectedItems.isEmpty()) {
        return clothingList
    }

    // Filter the list to include only items whose tag name is in the tagNames array
    return clothingList.filter { clothing -> clothing.tags.any { it.name in selectedItems } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBankDisplay(clothingListTemp: List<Clothing>) {

    var clickedClothing by remember { mutableStateOf<Clothing?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItems by remember { mutableStateOf(setOf<String>()) }
    val imageIds = remember { mutableStateOf(list) }
    val imageUrls = mutableListOf<String>()
    val imageLinks = mutableListOf<String>()
    val (isOverlayVisible, setIsOverlayVisible) = remember { mutableStateOf(false)}
    val clothingList = filterClothingByTagNames(clothingListTemp, selectedItems.toList())


    LaunchedEffect(isOverlayVisible){
        Log.d("MyBankDisplay", "isOverlayVisible er nu $isOverlayVisible")
        if(!isOverlayVisible){
            clickedClothing = null
        }
    }

    if (list != null) {
        for (item in list) {
            if (item is Clothing) {
                imageUrls.add(item.pictures[0])
                imageLinks.add(item.link)
            }
        }
    }
        if (viewModel.filter.filterItems.isNotEmpty()) {
            viewModel.filter.filterItems.forEach { filterItem ->
                selectedItems = selectedItems + filterItem.name
            }
            Log.d("KIG HER NUMSEMAND", "inde i if")
            //viewModel.filter.clearFilter()
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
                                selectedItems - item.name
                            }  else{
                                viewModel.filter.addFilterItem(item)
                                Log.d("Adding FilterItems", viewModel.filter.filterItems.joinToString { it.toString() })
                                selectedItems + item.name
                            }
                            GlobalState.selectedFilters = selectedItems.toList()
                        }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(alpha = 0f), shape = RoundedCornerShape(40.dp))
            ){
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ){
                    SettingsButton(drawerState)
                }
                Column {
                    Headertext2()
                    ImageList(
                        clothingList = clothingList,
                        imageUrls = imageUrls,
                        imageLinks = imageLinks
                    ) { clickedItem ->
                        clickedClothing = clickedItem
                    }
                    //Billedet af setting
                }
                if (clickedClothing != null) {
                    Overlay(
                        clothing = clickedClothing!!,
                        onCloseClicked = { clickedClothing = null }
                        //isOverlayVisible = isOverlayVisible,
                        //setIsOverlayVisible = setIsOverlayVisible
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
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center)
        {
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
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(23.dp))
            )
        }
    }
}



@Composable
fun Overlay(
    clothing: Clothing,
    //isOverlayVisible: Boolean,
    //setIsOverlayVisible: (Boolean) -> Unit
    onCloseClicked: () -> Unit
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
                .size(34.dp)
                .align(Alignment.TopEnd)
                .padding(6.dp)
                .clickable {
                    Log.d("Overlay", "luk knap klikket")
                    onCloseClicked()
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
            Box(
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
                    onPictureClick = {},
                    onSwipeRight = {},
                    onSwipeLeft = {}
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
                        onPictureClick = {},
                        onSwipeRight = {},
                        onSwipeLeft = {}
                    )
                }
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .width(180.dp)
                        .padding(start = 0.dp, top = 0.dp, end = 8.dp, bottom = 0.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0f),
                            shape = RoundedCornerShape(40.dp)
                        )
                ) {
                    pictureBox(
                        modifier = Modifier
                            .fillMaxSize(),
                        currentPiece.pictures[2],
                        onPictureClick = {},
                        onSwipeRight = {},
                        onSwipeLeft = {}
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
                    prisSkilt(
                        modifier = Modifier,
                        currentPiece.price
                    )
                }
            }
        }
    }
}

@Composable
fun Headertext2() { // Laver hovedetitel (Header)
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
        MyBankHeader()
    }
}
@Composable
fun MyBankHeader() { // Undertitel (Subheader)
    Row() {
        Text(text = "MY BANK",
            style = TextStyle(
                fontSize = 20.sp,
                //fontStyle = FontStyle.Roboto
            ),
            fontWeight = FontWeight.Bold,
        )
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