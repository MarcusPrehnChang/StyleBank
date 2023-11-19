package com.example.stylebank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.stylebank.data.ClothingRepository
import com.example.stylebank.model.Banner
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.ObservableList

class ProductViewModel(private val repository: ClothingRepository) { //List to listen to, using some kind of observable pattern, i believe Kotlin has a unique one that is best to use.
    private val listsMap: Map<String, ObservableList<*>> = mapOf(
        "product" to ObservableList<Clothing>(),
        "banner" to ObservableList<Banner>(),
        "likedItem" to ObservableList<Clothing>()
    )
    fun getList(key: String): ObservableList<Any>?{
        return listsMap[key] as? ObservableList<Any>
    }
}