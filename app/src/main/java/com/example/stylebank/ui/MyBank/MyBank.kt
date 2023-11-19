package com.example.stylebank

import com.example.stylebank.data.ClothingRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stylebank.ui.theme.StyleBankTheme

class MyBank : ComponentActivity() {

}
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
fun MyBank(imageIds: List<Int>){
    LazyVerticalGrid(columns = GridCells.Fixed(2)){
        items(imageIds.size){ index ->
            BankCloth(drawableResourceId = imageIds[index])
            }
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
    StyleBankTheme {
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(Modifier.verticalScroll(scrollState))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(0.dp, 77.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MYbankIcon()
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    BankCloth(drawableResourceId = R.drawable.jakke)
                    BankCloth(drawableResourceId = R.drawable.roedt)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    BankCloth(drawableResourceId = R.drawable.sortt)
                    BankCloth(drawableResourceId = R.drawable.groent)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    BankCloth(drawableResourceId = R.drawable.hvidt)
                    BankCloth(drawableResourceId = R.drawable.versacet)
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}