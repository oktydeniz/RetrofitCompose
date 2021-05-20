package com.example.retrofit_compose

import com.example.retrofit_compose.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {

    //https://api.nomics.com/v1/
    @GET("prices?key=79b383afc40a9eb2461b93318f3f8ede")
    fun getData(): Call<List<CryptoModel>>

}