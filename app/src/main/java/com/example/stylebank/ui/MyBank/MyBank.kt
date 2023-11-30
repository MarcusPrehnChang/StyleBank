package com.example.stylebank

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.ui.theme.StyleBankTheme
import com.example.stylebank.ui.theme.clothingObserver

class MyBank() : Fragment() {
    //MyBankDisplay()
}

val list = viewModel.getList("likedItem")
val bankObserver = object : ObservableListObserver<Any> {
    override fun onItemAdded(item: Any) {
        println("triggered")
    }
}
val add = viewModel.getList("likedItem")?.registerObserver(bankObserver)


@Composable
fun MyBankDisplay() {
    val imageIds = remember { mutableStateOf(list) }
    val imageUrls = mutableListOf<String>()
    if (list != null) {
        for(item in list){
            if(item is Clothing){
                imageUrls.add(item.pictures[0])
            }
        }
    }
    ImageList(imageUrls)
}




@Composable
fun ImageList(imageIds: List<String>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(imageIds.size) { index ->
            BankCloth(imageIds[index])
        }
    }
}

@Composable
fun BankCloth(imageUrl : String){
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