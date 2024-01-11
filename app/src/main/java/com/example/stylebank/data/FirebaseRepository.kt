package com.example.stylebank.data

import com.example.stylebank.model.Clothing
import com.example.stylebank.model.User
import com.example.stylebank.model.clothing.Tag
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class FirebaseRepository {
    var iterator: DocumentSnapshot? = null

    fun getBatchOfIds(callback: (List<String>) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val query = if (iterator != null){
            db.collection("products")
                .startAfter(iterator as QueryDocumentSnapshot)
                .limit(5)
        } else {
            db.collection("products")
                .limit(5)
        }

        query.get().addOnSuccessListener { result ->
            val idList = mutableListOf<String>()

            for (document in result){
                val id = document.id
                idList.add(id)
            }
            iterator = result.documents.lastOrNull()
            callback(idList)
        }
    }
    fun getOneId(callback: (List<String>) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val query = if (iterator != null){
            db.collection("products")
                .startAfter(iterator as QueryDocumentSnapshot)
                .limit(1)
        } else {
            db.collection("products")
                .limit(1)
        }

        query.get().addOnSuccessListener { result ->
            val idList = mutableListOf<String>()

            for (document in result){
                val id = document.id
                idList.add(id)
            }
            iterator = result.documents.lastOrNull()
            callback(idList)
        }
    }

    fun getClothing(clothingID : String, callback: (Clothing?) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("products")
            .document(clothingID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val id = documentSnapshot.id
                    println(id)
                    val brandName = documentSnapshot.getString("headtext1") ?: ""
                    val name = documentSnapshot.getString("headtext2") ?: " "
                    val link = documentSnapshot.getString("link") ?: " "
                    val price = documentSnapshot.getString("price") ?: " "
                    val pictures = documentSnapshot?.get("pictures") as? List<String> ?: emptyList()
                    val tagsStrings = documentSnapshot?.get("tags") as List<String> ?: emptyList()


                    var tags : MutableList<Tag> = mutableListOf()
                    for (string in tagsStrings) {
                        val tag = Tag(string, 100)
                        tags.add(tag)
                    }


                    val clothing = Clothing(pictures, brandName, name, price, link, id, tags)
                    callback(clothing)
                }else{
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                // Handle the error as needed
            }
    }

    fun getBatch(user : User, filter : Filter){
        for (i in 1..5){
            //getClothing()
        }
    }




}