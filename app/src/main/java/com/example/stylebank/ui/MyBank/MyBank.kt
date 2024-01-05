package com.example.stylebank

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.Filter
import com.example.stylebank.model.FilterItem
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.ui.theme.StyleBankTheme
import com.example.stylebank.ui.theme.clothingObserver

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
    if (list != null) {
        for(item in list){
            if(item is Clothing){
                imageUrls.add(item.pictures[0])
                imageLinks.add(item.link)
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                val items = listOf("Trøjer", "Bukser", "T-Shirts")
                items.forEach { item ->
                    val isSelected = selectedItems.contains(item)


                    NavigationDrawerItem(
                        label = { Text(text = item) },
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
                            selectedItems = if (selectedItems.contains(item)) {
                            selectedItems - item
                            //viewModel.filter.removeFilterItem(FilterItem(item))
                        }else{
                            selectedItems + item
                            //viewModel.filter.addFilterItem(FilterItem(item))
                        }

                        }
                    )

                }
            }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                //listen af billeder
                ImageList(imageUrls, imageLinks)

                //Billedet af setting
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ){
                    SettingsButton(drawerState)
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
fun ImageList(imageIds: List<String>, imageLinks: List<String>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(imageIds.size) { index ->
            BankCloth(imageIds[index], imageLinks[index])
        }
    }
}

@Composable
fun BankCloth(imageUrl : String, link : String){
    val context = LocalContext.current
    Box(modifier = Modifier
        .height(170.dp)
        .width(170.dp)){
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