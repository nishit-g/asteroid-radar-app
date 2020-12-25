package com.udacity.asteroidradar.main

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.PrivateConstants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.PictureOfDayApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDAO
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.*
import java.lang.Exception

enum class AsteroidApiStatus { LOADING, DONE }

class MainViewModel(private val database: AsteroidDatabase, application: Application) : ViewModel() {

    // TODO : Add Your own API KEY HERE
    private val apiKey = PrivateConstants.API_KEY

    // Create an instance of repository
    private val repository = AsteroidRepository(database)

    // Status for Progress Bar
    val status = repository.status
//    private var _asteroidList =  MutableLiveData<MutableList<Asteroid>>()
//
//    val asteroidList : LiveData<MutableList<Asteroid>>
//        get() = _asteroidList
//

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay


    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail : LiveData<Asteroid>
        get() = _navigateToAsteroidDetail


    init {
//        _asteroidList.value = dummyAsteroids()
        // Since we are now using repository
//        fetchAsteroidList()
        try{
            viewModelScope.launch {
                repository.refreshAsteroids()
                setupPictureOfDay()
            }
        }catch (e:Exception){
            Log.d(TAG, "${e.message}")
            status.value = AsteroidApiStatus.DONE
        }

    }


    val asteroidList = repository.asteroidsToShow

    private suspend fun setupPictureOfDay() {
        val result = PictureOfDayApiService.PictureOfDayApi.retrofitService.getPictureOfDay(apiKey).await()
        if(result.mediaType == "image") {
            _pictureOfDay.value = result
        }
    }

//    private fun fetchAsteroidList() {
//        viewModelScope.launch {
//            try {
//                val json = AsteroidApi.retrofitService.getAsteroid(apiKey)
//
//                val asteroids = parseAsteroidsJsonResult(JSONObject(json))
//
//                _asteroidList.value = asteroids
//            }catch (e : Exception){
//                Log.d("Error", "fetchAsteroidList: ${e.message}")
//            }
//        }
////        AsteroidApi.retrofitService.getAsteroid(apiKey).enqueue(object : Callback<String>{
////            override fun onResponse(call: Call<String>, response: Response<String>) {
////                    val json =JSONObject(response.body()!!)
////                    _asteroidList.value?.addAll(parseAsteroidsJsonResult(json))
////            }
////
////            override fun onFailure(call: Call<String>, t: Throwable) {
////                Log.d("FAILURE", t.message.toString())
////            }
////        })
//
//    }

    // For Checking purposes fill the asteroid list
    private fun dummyAsteroids() : MutableList<Asteroid>? {
        return mutableListOf(
                Asteroid(1,"DUM-01-NOW", "2021-20-12", 12.34, 1235.3, 55.6,1234.0, false),
                Asteroid(2,"DUM-02-THEN", "2021-30-12", 122.34, 35.3, 64.6,124.0, true),
                Asteroid(3,"DUM-03-BEFORE", "2025-10-01", 654.34, 235.3, 76.6,34.0, true)
                )
    }

    fun onNavigateToAsteroidDetail(asteroid : Asteroid){
        _navigateToAsteroidDetail.value = asteroid
    }

    fun doneNavigatingToAsteroidDetail(){
        _navigateToAsteroidDetail.value = null
    }



}