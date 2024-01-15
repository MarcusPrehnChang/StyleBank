package com.example.stylebank.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.CombinedData
import com.example.stylebank.model.Filter
import com.example.stylebank.model.ObservableList
import com.example.stylebank.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProductViewModel(private val repository: ClothingRepository) {
    val user = User()
    //List to listen to, using some kind of observable pattern, i believe Kotlin has a unique one that is best to use.
    var index by mutableStateOf(0)
    val filter = Filter()
    var isInitialized = false
    private var productList = ObservableList<Clothing>()
    private val bannerList = ObservableList<Banner>()
    private val likedList = ObservableList<Clothing>()



    init{
        if (!isTestEnvironment()){

            repository.updateList {
                val clothingList = repository.getProductList()
                for (clothing in clothingList){
                    productList.add(clothing)
                }

                val bannerList = repository.getBanners()
                for(banner in bannerList){
                    bannerList.add(banner)
                }

                val likedList = repository.getLikedItems()
                for (likedItem in likedList){
                    likedList.add(likedItem)
                }

                isInitialized = true
            }
        }
    }

    fun incrementIndex() : Boolean{
        println("product list size from incremenet start " +  productList.size)
        for (clothing in productList){
            println(clothing.firebaseId)
        }
        if (index == 7){
            println("Index was 7")
            val prevMap = productList
            val newMap = ObservableList<Clothing>()
            for (i in index..productList.size){
                newMap.add(prevMap[i])
            }
            val listOfObservers = prevMap.getObservers()
            prevMap.removeObservers()
            for (observer in listOfObservers){
                newMap.registerObserver(observer)
            }
            productList = newMap
            index = 0
            return true
        }
        if(index + 4 >= productList.size){
            println("index + 3 = true")
            val data = CombinedData(user.preferences, listOf("any"))
            GlobalScope.launch(Dispatchers.Default) {
                val result = repository.getClothes(data)
                println("result = " + result.size)
                productList.addAll(result)
                println(productList.size)
            }
        }
        index++
        return false
    }
    fun getList(key: String): ObservableList<Clothing>{
        return if(key == "product"){
            productList;
        }else {
            likedList
        }
    }


    fun addItem(key : String, item : Clothing){
        println(key)
        if(key == "product"){
            productList.add(item)
        }else if (key == "likedItem"){
            likedList.add(item)
        }
    }



    fun fetchOne(){
        println("fetchone called")
        repository.addOne {
            val result = repository.getProductList()
            addItem("product", result[result.size - 1])
        }
    }

    private fun isTestEnvironment() : Boolean{
        return System.getProperty("isTestEnvironment") == "true";
    }


}