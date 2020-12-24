package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    private var _asteroidList =  MutableLiveData<Asteroid>()

    val asteroidList : LiveData<Asteroid>
        get() {
            return _asteroidList
        }

    init {
        _asteroidList = dummyAsteroids()
    }

    fun dummyAsteroids() : MutableLiveData<Asteroid>{
        return Mutab
    }
}