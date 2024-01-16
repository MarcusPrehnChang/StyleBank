package com.example.stylebank.data

import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.CombinedData
import com.example.stylebank.model.User

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
                    firebaseRepository.getClothing(id) { clothing ->
                        if (clothing != null) {
                            productList.add(clothing)
                        }
                        completed++
                        if(completed == count){
                            onUpdateComplete()
                        }
                    }
                }
            }
        }
    }


    suspend fun getClothes(combinedData: CombinedData) : List<Clothing>{
        return serverCommunication.getBundle(combinedData)
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