package com.example.stylebank.stepDefinitions
import androidx.compose.runtime.Composable
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.ui.theme.structureOfScreen
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
                repo = ClothingRepository()
                viewModel = ProductViewModel(repo)

                waitForInit()
        }

        @Given("the user is on the clothing app")
        fun showClothingItem() {
                //We default showing the object, there is no logic needed except for the UI to show a specific instance to show an actual Item
        }
        @When("the user clicks on the like button")
        fun likeItem() {
                var list = viewModel.getList("product")
                var item = list?.get(0)
                viewModel.addItem("likedItems", item)
        }

        @Then("the clothing item is liked")
        fun isAdded() {
                var list = viewModel.getList("product")
                var item = list?.get(0)
                var likeList = viewModel.getList("likedItems")
                assert(likeList?.get(0) == item)
        }

        private fun waitForInit(){
                var timeout = 0
                while (!viewModel.isInitialized && timeout < 10000) {
                        Thread.sleep(100)
                        timeout += 100
                }
        }
}

