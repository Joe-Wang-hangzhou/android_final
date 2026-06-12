package com.example.hospitalregistration.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WhmRetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val api: WhmApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WhmApiService::class.java)
}
