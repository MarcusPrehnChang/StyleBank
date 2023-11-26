package com.example.stylebank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.stylebank.ui.SwipeInfo.SwipeScreen
import com.example.stylebank.ui.theme.StyleBankTheme
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.stylebank.ui.SwipeInfo.SwipeActivity

val roboto: FontFamily = FontFamily.Default
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()


            StyleBankTheme {
                NavHost(navController = navController, startDestination = "swipeScreen") {
                    composable("swipeActivity") {
                        SwipeActivity(navController = navController)
                    }
                    composable("MyBank") {
                        MyBank(navController = navController)
                    }
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SwipeActivity(navController)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = roboto,
                        fontWeight = FontWeight(600)
                    )
                ){
                    append("STYLE")
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = roboto,
                        fontWeight = FontWeight(300)
                    )
                ){
                    append("BANK")
                }
            }
        )
}