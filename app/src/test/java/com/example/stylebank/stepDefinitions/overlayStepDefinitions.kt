package com.example.stylebank.stepDefinitions

import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Clothing
import com.example.stylebank.viewmodel.ProductViewModel
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
class overlayStepDefinitions {
    private lateinit var repo: ClothingRepository
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        System.setProperty("isTestEnvironment", "true")
        repo = ClothingRepository()
        viewModel = ProductViewModel(repo)
    }
    @Given("I was swiping")
    fun showClothingItem(){
        val array = listOf("test")
        val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
        viewModel.addItem("product", clothing)
    }

    @When("I tap the clothing item for more information")
    fun likeItem(){
        //Only the navStack is responsible for actually choosing the page you are on.
        //The navstack is also responsible for showing additional information so we will just test if it is possible to retrieve it.
    }

    @Then("I want to see additional information")
    fun isAdded() {
        assert(viewModel.getList("product")?.size!! > 0)
        var listOfItems = viewModel.getList("product")
        var itemToCheck : Clothing? = listOfItems?.get(0) as Clothing
        assert(itemToCheck?.brandName != null)
        assert(itemToCheck?.objectName != null)
        assert(itemToCheck?.price != null)
        assert(itemToCheck?.link != null)
        assert(itemToCheck?.firebaseId != null)

    }
}