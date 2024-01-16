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
import com.example.stylebank.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductViewModel(private val repository: ClothingRepository) {
    val user = User()
    //List to listen to, using some kind of observable pattern, i believe Kotlin has a unique one that is best to use.
    var index by mutableStateOf(0)
    val filter = Filter()
    var isInitialized = false
    var selectedItems by mutableStateOf<Set<String>>(setOf())
        private set
    private var productList = ObservableList<Clothing>()
    private val bannerList = ObservableList<Banner>()
    private val likedList = ObservableList<Clothing>()
    private var gettingClothes = false;



    init{
        if (!isTestEnvironment()){
            GlobalScope.launch{
                withContext(Dispatchers.IO){
                    repository.getUser()
                    val bankedList = repository.getLikedItems()
                    likedList.addAll(bankedList)
                }
            }
            repository.updateList {
                val clothingList = repository.getProductList()
                for (clothing in clothingList){
                    productList.add(clothing)
                }

                val bannerList = repository.getBanners()
                for(banner in bannerList){
                    bannerList.add(banner)
                }

                isInitialized = true
            }
        }
    }

    fun incrementIndex() : Boolean{
        for (clothing in productList){
            println(clothing.firebaseId)
        }
        if (index >= 7){
            val prevMap = productList
            val newMap = ObservableList<Clothing>()
            for (i in index + 1 until productList.size){
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
        if(index + 5 >= productList.size && !gettingClothes){
            val data = CombinedData(user.preferences, listOf("any")) // <- listOf("any") er filteret
            GlobalScope.launch(Dispatchers.Default) {
                gettingClothes = !gettingClothes
                val result = repository.getClothes(data)
                productList.addAll(result)
                gettingClothes = !gettingClothes
            }
        }
        index++
        return false
    }

    fun filterList(){
        //Filtrer productlist
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
        }else if (key == "likedItem"){
            likedList.add(item)
            repository.saveLiked(likedList)

        }
    }

    private fun isTestEnvironment() : Boolean{
        return System.getProperty("isTestEnvironment") == "true";
    }


}