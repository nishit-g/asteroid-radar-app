package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<DatabaseAsteroid>)

    @Query("SELECT * FROM asteroids_table order by close_approach_date")
    fun getAll() : LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date = :date order by close_approach_date")
    fun getAsteroidsForDay(date: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date BETWEEN :startDate AND :endDate order by close_approach_date")
    fun getAsteroidsForWeek(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>

    @Query("delete from asteroids_table where close_approach_date = :day")
    fun deleteAsteroidsOfYesterday(day: String)
}