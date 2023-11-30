package com.example.stylebank

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.stylebank.ui.theme.structureOfScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.stylebank.model.Clothing
import com.example.stylebank.ui.theme.pictureBox
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.junit.Rule

class Like_cucumberTest {
        @Given("Showing clothing item")
        val productList = ArrayList<Clothing>()

        fun getProductList(): ArrayList<Clothing>{
            return productList
        }

        @When("^I click Greet Cucumber")
        fun bankButton() {
        }

        @Then("^Show Cucumber greeting")
        fun checkGreetingText(){
    }

