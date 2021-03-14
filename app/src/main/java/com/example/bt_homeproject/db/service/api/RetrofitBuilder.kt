package com.example.bt_homeproject.db.service.api

import com.example.bt_homeproject.utils.Constants.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder : Retrofit.Builder by lazy {
        Retrofit.Builder().
        baseUrl(baseUrl).
        addConverterFactory(GsonConverterFactory.create())
    }

    val apiService : ApiService by lazy{
        retrofitBuilder.build().
                create(ApiService::class.java)
    }
}