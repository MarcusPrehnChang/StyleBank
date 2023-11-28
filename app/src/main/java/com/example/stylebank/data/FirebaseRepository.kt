package com.example.stylebank.data

import com.example.stylebank.model.Clothing
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRepository {
    var iterator: QueryDocumentSnapshot? = null

    fun getBatchOfIds(callback: (List<String>) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val query = if (iterator != null){
            db.collection("products")
                .startAfter(iterator)
        } else {
            db.collection("products")
        }

        query.get().addOnSuccessListener { result ->
            val idList = mutableListOf<String>()

            for (document in result){
                val id = document.id
                idList.add(id)
            }
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
                    val price = documentSnapshot.getDouble("price") ?: 0.0
                    val pictures = documentSnapshot.get("pictures") as? List<String> ?: emptyList()


                    val clothing = Clothing(pictures as Array<String>, brandName, name, id, link, price)
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