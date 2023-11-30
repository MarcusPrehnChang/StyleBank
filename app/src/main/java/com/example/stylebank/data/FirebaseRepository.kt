package com.example.stylebank.data

import com.example.stylebank.model.Clothing
import com.google.firebase.firestore.DocumentSnapshot
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
            println("SuccessListener was called")
            val idList = mutableListOf<String>()

            for (document in result){
                val id = document.id
                idList.add(id)
            }
            iterator = result.documents.lastOrNull()
            //println(idList.size)
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
            //println(idList.size)
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
                    val brandName = documentSnapshot.getString("headtext1") ?: ""
                    val name = documentSnapshot.getString("headtext2") ?: " "
                    val link = documentSnapshot.getString("link") ?: " "
                    val price = documentSnapshot.getString("price") ?: " "
                    val pictures = documentSnapshot?.get("pictures") as? List<String> ?: emptyList()

                    println("pcitures array was : " + pictures.size)
                    val clothing = Clothing(pictures, brandName, name, price, link, id)
                    callback(clothing)
                }else{
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
                // Handle the error as needed
            }
    }


}