package com.example.stylebank.data.api

import com.example.stylebank.model.CombinedData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClothingApi {

    @POST("api/process")
    fun processResource(@Body data: CombinedData): Call<Any>

}
