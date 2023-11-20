package com.example.stylebank.data

import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing

class ClothingRepository {
    private val productList = ArrayList<Clothing>()
    private val banners = ArrayList<Banner>()
    private val likedItems = ArrayList<Clothing>()

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