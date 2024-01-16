package com.example.stylebank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Clothing
import com.example.stylebank.ui.Animation.StyleBankSplashScreen
import com.example.stylebank.ui.theme.MenubarGray
import com.example.stylebank.ui.theme.StyleBankTheme
import com.example.stylebank.ui.theme.structureOfScreen
import com.example.stylebank.ui.whatsHot.WholeScreen
import com.example.stylebank.viewmodel.ProductViewModel

val roboto: FontFamily = FontFamily.Default

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StyleBankTheme {
                app()
            }
        }
    }
}
object GlobalState {
    var selectedFilters: List<String> = listOf()
    // You can add more global properties or functions here
}
val repository = ClothingRepository()
val viewModel = ProductViewModel(repository)

@Composable
fun app(){
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "splash_screen"
    ){
        composable("splash_screen"){
            StyleBankSplashScreen(navController)
        }
        composable("whats_hot"){
            WholeScreen()
        }
        composable("swipeFragment"){
            structureOfScreen()
        }
        composable("myBank"){
            val clothingList = viewModel.getList("likedItem").orEmpty().filterIsInstance<Clothing>()
            MyBankDisplay(clothingList)
        }

        composable("whatsHot"){
            WholeScreen()
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomBar(navController = navController)
    }

}

@Composable
fun BottomBar(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .height(70.dp)
            .padding(16.dp, 0.dp)
            .background(
                color = MenubarGray,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
        contentAlignment = Alignment.Center

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            MenuBarButton(
                onClick = { navController.navigate("whatsHot") },
                icon = painterResource(id = R.drawable.icon_ild),
                iconSize = 24.dp

            )

            MenuBarButton(
                onClick = { navController.navigate("swipeFragment") },
                icon = painterResource(id = R.drawable.icon_swipe),
                iconSize = 38.dp
            )

            MenuBarButton(
                onClick = { navController.navigate("myBank") },
                icon = painterResource(id = R.drawable.icon_mb),
                iconSize = 24.dp
            )
        }
    }
}

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


