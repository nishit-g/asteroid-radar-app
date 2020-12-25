package com.udacity.asteroidradar.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data : List<Asteroid>)

    @Query("SELECT * FROM asteroids_table")
    fun getAll() : MutableLiveData<Asteroid>

}