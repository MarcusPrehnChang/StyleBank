package com.example.stylebank.viewmodel

import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing

class ProductViewModel { //List to listen to, using some kind of observable pattern, i believe Kotlin has a unique one that is best to use.
    private val productList = ArrayList<Clothing>()
    private val banners = ArrayList<Banner>()
    private val likedItems = ArrayList<Clothing>()
}