package com.example.stylebank.model

import com.example.stylebank.model.clothing.Tag

class Clothing(val pictures: List<String> = emptyList<String>(), val objectName: String, val brandName: String, val price: String, val link: String, val firebaseId: String, val tags: MutableList<Tag> = mutableListOf()) {
}