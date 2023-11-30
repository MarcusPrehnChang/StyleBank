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
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.ui.theme.StyleBankTheme

class MyBank() : Fragment() {
    //MyBankDisplay()
}
val imageID = arrayOf(
    R.drawable.image1,
    R.drawable.image2,
    R.drawable.image3,
    R.drawable.image4,
    R.drawable.image5,
    R.drawable.image6,
    R.drawable.sb_skjorte,
    R.drawable.skagen1,
    R.drawable.skagen2,
    R.drawable.skagen3,
    )
//val repository = ClothingRepository()
//val viewModel = ProductViewModel(repository)


@Composable
fun MyBankDisplay() {
    val (imageIds, setImageIds) = remember { mutableStateOf(imageID.toList()) }
    val clothingObserver = object : ObservableListObserver<Any> {
        //private var onItemAddedListener: ((item: Any) -> Unit)? = null
        override fun onItemAdded(item: Any) {
            println("check")
            //onItemAddedListener?.invoke(item)
            val newImageId = R.drawable.image1
            setImageIds(imageIds + listOf(newImageId))
        }
    }
    viewModel.getList("likedItem")?.registerObserver(clothingObserver)

    ImageList(imageIds = imageIds) {
        // Add a new image ID to the list
        val newImageId = R.drawable.image1
        setImageIds(imageIds + listOf(newImageId))
    }




}




@Composable
fun ImageList(imageIds: List<Int>, onAddImageClick: () -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(imageIds.size) { index ->
            BankCloth(drawableResourceId = imageIds[index])
        }
    }
    AddImageButton(){
        val array = arrayOf("test")
        //val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
        //viewModel.getList("likedItem")?.add(clothing)
    }
}

@Composable
fun AddImageButton(onClick: () -> Unit) {
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
fun BankCloth(drawableResourceId: Int){
    Image(
        painter = painterResource(drawableResourceId),
        contentDescription = null,
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