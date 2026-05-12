package com.app.tallerRetrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecetasInstance {
    private const val BASE_URL = "https://www.themealdb.com/"

    val api: RecetasApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecetasApiService::class.java)
    }
}