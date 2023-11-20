package com.example.stylebank

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylebank.ui.theme.StyleBankTheme

class MyBank : ComponentActivity() {}
val imageID = arrayOf(
    R.drawable.jakke,
    R.drawable.roedt,
    R.drawable.sortt,
    R.drawable.groent,
    R.drawable.hvidt,
    R.drawable.versacet,
    R.drawable.jakke,
    R.drawable.roedt,
    R.drawable.sortt,
    R.drawable.groent,
    R.drawable.hvidt,
    R.drawable.versacet,
    R.drawable.jakke
    )

@Composable
fun MyBankDisplay(){
    val (imageIds, setImageIds) = remember { mutableStateOf(imageID.toList()) }

    ImageList(imageIds = imageIds) {
        // Add a new image ID to the list
        val newImageId = R.drawable.roedt
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

    AddImageButton(onClick = onAddImageClick)
}
@Composable
fun AddImageButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Add Image to List")
    }
}

@Composable
fun MYbankIcon(){

    Image(
        painter = painterResource(id = R.drawable.mybank),
        contentDescription = null,
        modifier = Modifier
            .height(81.dp)
            .width(152.dp)
    )
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
    val imageIdState = remember{ mutableStateOf(listOf<Int>())}
    StyleBankTheme {
        MyBankDisplay()
    }
}