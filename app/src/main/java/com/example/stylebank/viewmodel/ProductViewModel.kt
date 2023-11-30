package com.example.stylebank.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableList

class ProductViewModel(private val repository: ClothingRepository) {
    //List to listen to, using some kind of observable pattern, i believe Kotlin has a unique one that is best to use.
    var index by mutableStateOf(0)
    var isInitialized = false
    private val listsMap: Map<String, ObservableList<out Any>> = mapOf(
        "product" to ObservableList<Clothing>(),
        "banner" to ObservableList<Banner>(),
        "likedItem" to ObservableList<Clothing>()
    )
    init{
        if (!isTestEnvironment()){
            repository.updateList {
                val clothingList = repository.getProductList()
                for (clothing in clothingList){
                    (listsMap["product"] as? ObservableList<Clothing>)?.add(clothing)
                }
                val bannerList = repository.getBanners()
                for(banner in bannerList){
                    (listsMap["banner"] as? ObservableList<Banner>)?.add(banner)
                }
                val likedList = repository.getLikedItems()
                for (likedItem in likedList){
                    (listsMap["likedItem"] as? ObservableList<Clothing>)?.add(likedItem)
                }
                isInitialized = true
            }
        }
    }

    fun getList(key: String): ObservableList<Any>?{
        return listsMap[key] as? ObservableList<Any>
    }


    fun <T> addItem(key : String, item : T){
        (listsMap[key] as? ObservableList<T>)?.add(item)
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