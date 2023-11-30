package com.example.stylebank.stepDefinitions
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Clothing
import com.example.stylebank.viewmodel.ProductViewModel
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
class LikeCucumberTest {
        private lateinit var repo: ClothingRepository
        private lateinit var viewModel: ProductViewModel

        @Before
        fun setUp() {
                System.setProperty("isTestEnvironment", "true")
                repo = ClothingRepository()
                viewModel = ProductViewModel(repo)
        }

        @Given("the user is on the clothing app")
        fun showClothingItem() {
                val array = listOf("test")
                val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
                viewModel.addItem("product", clothing)
        }
        @When("the user clicks on the like button")
        fun likeItem() {
                var list = viewModel.getList("product")
                var item = list?.get(0)
                viewModel.addItem("likedItem", item)
        }

        @Then("the clothing item is liked")
        fun isAdded() {
                var list = viewModel.getList("product")
                var item = list?.get(0)
                var likeList = viewModel.getList("likedItem")
                assert(likeList?.get(0) == item)
        }
}

