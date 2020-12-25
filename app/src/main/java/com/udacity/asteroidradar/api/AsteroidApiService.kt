package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// Retrofit builder
private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

interface AsteroidApiServiceInterface {

    // Since we need to get string and then convert it to json objects
    @GET("/neo/rest/v1/feed")
    fun getAsteroid(@Query("api_key") apiKey:String ):
        Call<String>
}


object AsteroidApi {
    val retrofitService : AsteroidApiServiceInterface by lazy {
        retrofit.create(AsteroidApiServiceInterface::class.java)
    }
}