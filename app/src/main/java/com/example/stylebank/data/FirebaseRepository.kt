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

    fun getBatch(tag: List<Tag>) : List<Clothing>{
        for (element in tag){
            println("tag name = " + element.name)
        }
        println("getBatch was reached")
        println("tag.size = " + tag.size)
        println("cocka")
        println(tag[0].name)
        for (element in tag){
            println(element.name)
        }
        val db = FirebaseFirestore.getInstance()
        val result = mutableListOf<Clothing>()
        var clothing = Clothing(brandName = "test", firebaseId = "testID", link = "google.com", objectName = "Test object", price = "ass")
        for (i in 0..4){
            if(tag[i].name == "any"){
                result.add(getRandom())
            }else{
                db.collection("products").whereArrayContains("tags", tag[i].name)
                    .get().addOnSuccessListener { querySnapshot ->
                        if(!querySnapshot.isEmpty){
                            val document = querySnapshot.documents
                            clothing = if(document.isNotEmpty()){
                                val randomDoc = document.shuffled().first()
                                makeClothing(randomDoc)
                            }else{
                                getRandom()
                            }
                        }
                    }.addOnFailureListener{exception ->
                        clothing = getRandom()
                        result.add(clothing)
                    }
                result.add(clothing)
            }
        }
        return result
    }

    private fun getRandom() : Clothing{
        val db = FirebaseFirestore.getInstance()
        val collectionPath = "products" // Replace with your actual collection path
        var clothing = Clothing(brandName = "test", firebaseId = "testID", link = "google.com", objectName = "Test object", price = "ass")
        // Create a query for the "clothing" collection
        db.collection(collectionPath)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val randomDocument = querySnapshot.documents.shuffled().firstOrNull()

                    clothing = randomDocument?.let { makeClothing(it) }!!
                } else {
                    println("query yielded empty")
                }
            }
            .addOnFailureListener { exception ->
                println({"Couldnt query"})
            }
        return clothing
    }


    private fun makeClothing(document: DocumentSnapshot) : Clothing{
        val tagsStrings = document.get("tags") as List<String> ?: emptyList()

        val tags: MutableList<Tag> = mutableListOf()
        for (string in tagsStrings) {
            val tag = Tag(string, 100)  // You might adjust the second parameter as needed
            tags.add(tag)
        }

        return Clothing(
            document.get("pictures") as? List<String> ?: emptyList(),
            document.getString("headtext1") ?: "",
            document.getString("headtext2") ?: "",
            document.getString("price") ?: "",
            document.getString("link") ?: "",
            document.id,
            tags
        )
    }


}