package com.example.stylebank.data

import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing

class ClothingRepository {
    private val productList = ArrayList<Clothing>()
    private val banners = ArrayList<Banner>()
    private val likedItems = ArrayList<Clothing>()
    private var firebaseRepository: FirebaseRepository = FirebaseRepository()
    private var serverCommunication: ServerCommunication = ServerCommunication(firebaseRepository)


    fun updateList(onUpdateComplete: () -> Unit){
        serverCommunication.getClothing{ result ->
            if (result != null) {
                val count = result.size
                var completed = 0
                for (id in result){
                    //println("adding id : $id")
                    firebaseRepository.getClothing(id) { clothing ->
                        if (clothing != null) {
                            productList.add(clothing)
                            println("added clothing to productList")
                        }
                        completed++
                        if(completed == count){
                            onUpdateComplete()
                        }
                    }
                }
            }
            println(productList.size)
        }
    }

    fun getProductList(): ArrayList<Clothing>{
        return productList
    }
    fun getBanners(): ArrayList<Banner>{
        return banners
    }
    fun getLikedItems(): ArrayList<Clothing>{
        return likedItems
    }



}