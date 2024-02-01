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
            val itemsToGet : List<String>?
            if(filter.filterItems.isNotEmpty()){
                // Her skal itemsToGet vaere lig filter.filterItems
                itemsToGet = filter.filterItems.map { it.name}
            }
            else{
                // hvis der ikke er valgt nogle filtre
                itemsToGet = listOf("any")
            }
            val data = CombinedData(user.preferences, itemsToGet) // Her hentes nyt toej ned listOf("any") er hvis der ikke er filter til
            GlobalScope.launch(Dispatchers.Default) {
                gettingClothes = !gettingClothes
                val result = repository.getClothes(data)
                productList.addAll(result)
                gettingClothes = !gettingClothes
                println("finished")
            }


            repository.updateList {

                val bannerList = repository.getBanners()
                for(banner in bannerList){
                    bannerList.add(banner)
                }

                isInitialized = true
            }
        }
    }

    fun incrementIndex() : Boolean{

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
            val itemsToGet : List<String>?
            if(filter.filterItems.isNotEmpty()){
                // Her skal itemsToGet vaere lig filter.filterItems
                itemsToGet = filter.filterItems.map { it.name}
            }
            else{
                // hvis der ikke er valgt nogle filtre
                itemsToGet = listOf("any")
            }
            val data = CombinedData(user.preferences, itemsToGet) // Her hentes nyt toej ned listOf("any") er hvis der ikke er filter til
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
    fun filtrerList() {
        println("FiltrerList was called")
        val itemsToGet : List<String>?
        if(filter.filterItems.isNotEmpty()){
            // Her skal itemsToGet vaere lig filter.filterItems
            itemsToGet = filter.filterItems.map { it.name}
        }
        else{
            // hvis der ikke er valgt nogle filtre
            itemsToGet = listOf("any")
        }
        val data = CombinedData(user.preferences, itemsToGet) // Her hentes nyt toej ned listOf("any") er hvis der ikke er filter til
        GlobalScope.launch(Dispatchers.Default) {
            gettingClothes = true
            var tempFilterListe = mutableListOf<Clothing>()
            for(i in index until productList.size  ){
                tempFilterListe.add(productList[i])
            }

            // Iterate over the products using the iterator
            val iterator = tempFilterListe.iterator()

            // Iterate over the products using the iterator
            while (iterator.hasNext()) {
                val product = iterator.next()
                // Check each filterItem for a match in the product tags
                for (filterItem in filter.filterItems) {
                    if (!product.tags.any { it.name == filterItem.name }) {
                        // Remove the product using the iterator
                        iterator.remove()
                        break // Stops the inner loop
                    }
                }
            }
            val x = productList.size
            productList.addAll(tempFilterListe)
            index = x
            incrementIndex()
            val result = repository.getClothes(data)
            productList.addAll(result)
            gettingClothes = false
        }
        // Create an iterator for safe removal
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