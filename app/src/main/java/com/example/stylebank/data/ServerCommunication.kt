package com.example.stylebank.data

import com.example.stylebank.data.api.ClothingApi
import com.example.stylebank.model.CombinedData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerCommunication(firebaseRepository: FirebaseRepository) {
    private val firebaseRepository = firebaseRepository

    private val clothingApi: ClothingApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://sbs2-lzpt.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        clothingApi = retrofit.create(ClothingApi::class.java)
    }

    fun getBundle(combinedData : CombinedData){
        GlobalScope.launch(Dispatchers.IO){
            val call : Call<Any> = clothingApi.processResource(combinedData)
            call.enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
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