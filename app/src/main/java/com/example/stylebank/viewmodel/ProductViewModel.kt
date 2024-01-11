package com.example.stylebank.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.Filter
import com.example.stylebank.model.ObservableList
import com.example.stylebank.model.User

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
    /*
    fun getFilteredItems(): List<Clothing> {
        val allProducts = listsMap["product"] as? ObservableList<Clothing> ?: return emptyList()
        return allProducts.filter { clothing ->
            filter.someFilterCondition(clothing)
        }
    }

     */


    fun incrementIndex(){
        index++
        if (index == 7){
            val prevMap = productList
            val newMap = ObservableList<Clothing>()
            for (i in index..10){
                newMap.add(prevMap[i])
            }
            val listOfObservers = prevMap.getObservers()
            prevMap.removeObservers()

            for (observer in listOfObservers){
                newMap.registerObserver(observer)
            }

            productList = newMap



        }
    }
    fun getList(key: String): ObservableList<Clothing>{
        return if(key == "product"){
            productList;
        }else {
            likedList
        }
    }


    fun addItem(key : String, item : Clothing){
        if(key == "product"){
            productList.add(item)
        }else if (key == "likeditem"){
            productList.add(item)
        }
    }


    fun fetchBatch(){
        repository.updateList(){

        }
        val result = repository.getProductList()
        for (clothes in result){
            addItem("product", clothes)
        }
    }

    fun fetchOne(){
        repository.addOne {
            val result = repository.getProductList()
            addItem("product", result[result.size - 1])
        }
    }
    private fun isTestEnvironment() : Boolean{
        return System.getProperty("isTestEnvironment") == "true";
    }


}