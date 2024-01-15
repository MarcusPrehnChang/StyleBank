package com.example.stylebank.data

import com.example.stylebank.data.api.ClothingApi
import com.example.stylebank.model.Clothing
import com.example.stylebank.model.CombinedData
import com.example.stylebank.model.clothing.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerCommunication(firebaseRepository: FirebaseRepository) {
    private val firebaseRepository = firebaseRepository
    private val clothingApi: ClothingApi
    private val backupBatch : List<Tag> = listOf(Tag("any", 100), Tag("any", 100), Tag("any", 100), Tag("any", 100), Tag("any", 100))

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sbs2-lzpt.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        clothingApi = retrofit.create(ClothingApi::class.java)
    }

    suspend fun getBundle(combinedData: CombinedData): List<Clothing> = withContext(Dispatchers.IO) {
        var result = emptyList<Clothing>()
        println("reached getBundle")
        println(combinedData.tagList.size)
        try {
            println("in try")
            val call: Call<List<Tag>> = clothingApi.processResource(combinedData)
            val response = call.execute()

            result = if (response.isSuccessful) {
                println("response successful")
                val tags: List<Tag>? = response.body()
                println("response body : " + response.body())
                if (tags != null) {
                    firebaseRepository.getBatch(tags)
                } else {
                    firebaseRepository.getBatch(backupBatch)
                }
            } else {
                println("Response unsuccessful")
                firebaseRepository.getBatch(backupBatch)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //result = firebaseRepository.getBatch(backupBatch)
        }

        return@withContext result
    }



    fun getClothing(callback: (List<String>?) -> Unit) {
        val resultOfQuery = mutableListOf<String>()
        firebaseRepository.getBatchOfIds { batchOfIds ->
            for (id in batchOfIds){
                resultOfQuery.add(id)
            }
            callback(resultOfQuery)
        }
        //TODO("This is what the algorithm should end up doing, this is just dummy implementation so that the app has a consistent flow")
    }

    fun getOneClothingId(callback: (List<String>?) -> Unit) {
        val resultOfQuery = mutableListOf<String>()
        firebaseRepository.getOneId { batchOfIds ->
            for (id in batchOfIds){
                resultOfQuery.add(id)
            }
            callback(resultOfQuery)
        }
        //TODO("This is what the algorithm should end up doing, this is just dummy implementation so that the app has a consistent flow")
    }



}