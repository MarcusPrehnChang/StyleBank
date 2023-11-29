package com.example.stylebank

import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableListObserver
import com.example.stylebank.viewmodel.ProductViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ViewModelTest {
    private lateinit var viewModel : ProductViewModel
    private lateinit var repo : ClothingRepository


    @Before
    fun setup(){
        repo = ClothingRepository()
        viewModel = ProductViewModel(repo)
    }

    @Test
    fun testAddItem(){
        val newItem = "test"

        viewModel.addItem("product", newItem)

        val liveData = viewModel.getList("product")

        assertEquals(1, liveData?.size)
        assertEquals(newItem, liveData?.get(0))

    }

    @Test
    fun testObserve() {
        var gotNotification = false

        val observer = object : ObservableListObserver<Any> {
            override fun onItemAdded(item: Any) {
                gotNotification = true
            }

            /*override fun setOnItemAddedListener(listener: ((item: Any) -> Unit)?) {
                TODO("Not yet implemented")
            }*/
        }
        viewModel.getList("product")?.registerObserver(observer)
        val array = arrayOf("test")
        val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
        viewModel.addItem("product", clothing)
        assertTrue(gotNotification)
    }

    @Test
    fun properUpdate() {
        var clothingGet = Clothing(arrayOf(), "", "", "0", "", "")

        val observer = object : ObservableListObserver<Any> {
            override fun onItemAdded(item: Any) {
                clothingGet = item as Clothing
            }

            /*override fun setOnItemAddedListener(listener: ((item: Any) -> Unit)?) {
                //
            }*/
        }
        viewModel.getList("product")?.registerObserver(observer)
        val array = arrayOf("test")
        val clothing = Clothing(array, "testName", "testBrand", "123", "123", "123")
        viewModel.addItem("product", clothing)
        assertEquals(clothingGet, clothing)
    }

}