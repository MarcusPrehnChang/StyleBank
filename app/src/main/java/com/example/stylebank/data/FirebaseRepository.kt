package com.example.stylebank.data

import com.example.stylebank.model.Clothing
import com.example.stylebank.model.User
import com.example.stylebank.model.clothing.Tag
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getBatch(tag: List<Tag>): List<Clothing> = coroutineScope {
        val db = FirebaseFirestore.getInstance()
        val result = mutableListOf<Clothing>()

        for (i in 0 until 5) {
            if (tag[i].name == "any") {
                val randomClothing = getRandom()
                result.add(randomClothing)
            } else {
                try {
                    val querySnapshot = db.collection("products").whereArrayContains("tags", tag[i].name).get().await()

                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents
                        val clothing = if (document.isNotEmpty()) {
                            val randomDoc = document.shuffled().first()
                            makeClothing(randomDoc)
                        } else {
                            getRandom()
                        }
                        result.add(clothing)
                    }
                } catch (exception: Exception) {
                    println("Firestore query failed: $exception")
                    result.add(getRandom())
                }
            }
        }
        return@coroutineScope result
    }

    suspend fun getRandom(): Clothing = suspendCoroutine { continuation ->
        val db = FirebaseFirestore.getInstance()
        val collectionPath = "products"
        var clothing = Clothing(brandName = "test", firebaseId = "testID", link = "google.com", objectName = "Test object", price = "ass")
        db.collection(collectionPath)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val randomDocument = querySnapshot.documents.shuffled().firstOrNull()
                    clothing = randomDocument?.let { makeClothing(it) }!!
                } else {
                    println("query yielded empty")
                }
                continuation.resume(clothing)
            }
            .addOnFailureListener { exception ->
                println("Could not query")
                continuation.resume(clothing)
            }
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

    suspend fun getClothingAsync(clothingID : String): Clothing = suspendCoroutine { continuation ->
        val db = FirebaseFirestore.getInstance()
        val collectionPath = "products"
        var clothing = Clothing(brandName = "test", firebaseId = "testID", link = "google.com", objectName = "Test object", price = "ass")
        db.collection(collectionPath)
            .document(clothingID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    clothing = makeClothing(documentSnapshot)
                } else {
                    println("query yielded empty")
                }
                continuation.resume(clothing)
            }
            .addOnFailureListener { exception ->
                println("Could not query")
                continuation.resume(clothing)
            }
    }

    fun saveLiked(likedItems : List<Clothing>){
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Exam").document("examUser")
        val newLikedValues = likedItems.map { it.firebaseId }
        val data = hashMapOf(
            "liked" to newLikedValues
        )
        document.update(data as Map<String, Any>)
    }

    suspend fun getUser() : List<String>{
        return suspendCoroutine { continuation ->
            val db = FirebaseFirestore.getInstance()
            val document = db.collection("Exam").document("examUser")
            var likedItems = emptyList<String>()
            document.get().addOnSuccessListener {documentSnapshot ->
                likedItems = documentSnapshot?.get("liked") as List<String>
                continuation.resume(likedItems)
            }
        }
    }



}