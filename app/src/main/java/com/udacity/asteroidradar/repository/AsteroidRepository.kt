package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PrivateConstants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.main.AsteroidApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database : AsteroidDatabase) {
    // TODO : Add Your own API KEY HERE
    private val apiKey = PrivateConstants.API_KEY


    private var today = ""
    private var week = ""
    private var yesterday = ""
    init {
        val dataFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        today = dataFormat.format(calendar.time)

        calendar.add(Calendar.DAY_OF_YEAR, 7)
        week = dataFormat.format(calendar.time)

        calendar.add(Calendar.DAY_OF_YEAR,-8)
        yesterday = dataFormat.format(calendar.time)

        Log.d("ZEBRA", "$today + $week + $yesterday")
    }


    val status = MutableLiveData<AsteroidApiStatus>()

    // UI's will observe this
    val asteroidsToShow: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDAO.getAll()) {
                it.asDomainModel()
            }

    val todayAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDatabaseDAO.getAsteroidsForDay(today)) {
        it.asDomainModel()
    }

    val weeklyAsteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDatabaseDAO.getAsteroidsForWeek(today, week)) {
        it.asDomainModel()
    }


    // Function to retrieve and Store it in the database
    suspend fun refreshAsteroids(){
        status.value = AsteroidApiStatus.LOADING
        withContext(Dispatchers.IO){
            // This will return json String
            val json = AsteroidApi.retrofitService.getAsteroid(apiKey)

            // Parsing jsonString
            // Will return ArrayList<Asteroid>
            val asteroids = parseAsteroidsJsonResult(JSONObject(json))

            // Transform ArrayList<Asteroid> to MutableList<DatabaseAsteroid>
            val transformedAsteroids = mutableListOf<DatabaseAsteroid>()

            // Transform and add it to the list
            for (asteroid in asteroids){

                val databaseAsteroid = DatabaseAsteroid(
                        asteroid.id,
                        asteroid.codename,
                        asteroid.closeApproachDate,
                        asteroid.absoluteMagnitude,
                        asteroid.estimatedDiameter,
                        asteroid.relativeVelocity,
                        asteroid.distanceFromEarth,
                        asteroid.isPotentiallyHazardous
                )

                transformedAsteroids.add(databaseAsteroid)
            }

            // Insert the transformed List to database
            database.asteroidDatabaseDAO.insertAll(transformedAsteroids.toList())
        }
        status.value = AsteroidApiStatus.DONE
    }

    suspend fun deleteAsteroids(){
        withContext(Dispatchers.IO){
            database.asteroidDatabaseDAO.deleteAsteroidsOfYesterday(yesterday)
        }
    }
}