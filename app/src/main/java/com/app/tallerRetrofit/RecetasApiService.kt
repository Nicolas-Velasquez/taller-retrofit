package com.app.tallerRetrofit

import retrofit2.http.GET

interface RecetasApiService {
    @GET("api/json/v1/1/random.php")
    suspend fun getRandomReceta(): RecetaResponse
}