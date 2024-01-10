package com.example.stylebank

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.FilterItem
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.ui.theme.StyleBankTheme


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
fun MyBankDisplay() {

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
            Box(modifier = Modifier.fillMaxSize()) {
                //listen af billeder
                ImageList(imageUrls, imageLinks) {
                    setIsOverlayVisible(true)
                }
                //Billedet af setting
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ){
                    SettingsButton(drawerState)
                }
                if (isOverlayVisible){
                    Overlay()
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
        painter = painterResource(id = R.drawable.icon_filter),
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clickable { openDrawer = true } // Opdater tilstanden her
    )
}


@Composable
fun ImageList(imageIds: List<String>, imageLinks: List<String>,onClothClicked: () -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(imageIds.size) { index ->
            BankCloth(imageIds[index], imageLinks[index], onClothClicked)
            }
        }
    }


@Composable
fun BankCloth(imageUrl : String, link : String, onClothClicked: () -> Unit){
    val context = LocalContext.current
    Box(modifier = Modifier
        .height(170.dp)
        .width(170.dp)
        .clickable { onClothClicked() }
    ){
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.loading)
                }
            ), contentDescription = null,
            modifier = Modifier
                .height(170.dp)
                .width(170.dp)
        )
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
fun Overlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Your overlay content goes here
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