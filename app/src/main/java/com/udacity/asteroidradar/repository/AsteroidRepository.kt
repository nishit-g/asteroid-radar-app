package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PrivateConstants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database : AsteroidDatabase) {
    // TODO : Add Your own API KEY HERE
    private val apiKey = PrivateConstants.API_KEY
    // UI's will observe this
    val asteroidsToShow: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDAO.getAll()) {
                it.asDomainModel()
            }


    // Function to retrieve and Store it in the database
    suspend fun refreshAsteroids(){
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
    }
}