package com.example.stylebank.stepDefinitions

import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Clothing
import com.example.stylebank.viewmodel.ProductViewModel
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class likeStepDefinitions {
    private lateinit var repo: ClothingRepository
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        System.setProperty("isTestEnvironment", "true")
        repo = ClothingRepository()
        viewModel = ProductViewModel(repo)
    }
    @Given("The app has opened")
    fun showClothingItem(){
        val array = listOf("test")
        val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
        viewModel.addItem("product", clothing)
    }

    @When("i go to the swipe section")
    fun likeItem(){
        //Only the navStack is responsible for actually choosing the page you are on.
    }

    @Then("i can see clothing items")
    fun isAdded() {
        assert(viewModel.getList("product")?.size!! > 0)
        }
}