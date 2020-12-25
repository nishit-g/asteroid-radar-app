package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    private var _asteroidList =  MutableLiveData<MutableList<Asteroid>>()

    val asteroidList : LiveData<MutableList<Asteroid>>
        get() = _asteroidList


    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()

    val navigateToAsteroidDetail : LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    init {
        _asteroidList.value = dummyAsteroids()
    }

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