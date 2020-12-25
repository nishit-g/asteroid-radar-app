package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Moshi Builder
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


interface AsteroidApiServiceInterface {

    // Since we need to get string and then convert it to json objects
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroid(@Query("api_key") apiKey:String ):
        String
}


object AsteroidApi {
    // Retrofit builder
    private val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .build()


    val retrofitService : AsteroidApiServiceInterface by lazy {
        retrofit.create(AsteroidApiServiceInterface::class.java)
    }
}