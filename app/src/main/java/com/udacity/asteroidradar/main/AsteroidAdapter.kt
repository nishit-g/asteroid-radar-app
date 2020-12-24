package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {
    var data = listOf<Asteroid>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.single_asteroid,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAsteroid = data[position]

        holder.asteroidMainTitle.text = currentAsteroid.codename
        holder.asteroidDate.text = currentAsteroid.closeApproachDate

        if (currentAsteroid.isPotentiallyHazardous) {
            holder.image.setImageResource(R.drawable.ic_status_potentially_hazardous)
        } else {
            holder.image.setImageResource(R.drawable.ic_status_normal)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val asteroidMainTitle : TextView = itemView.findViewById(R.id.tv_asteroid_main_text)
        val asteroidDate : TextView = itemView.findViewById(R.id.tv_asteroid_date)
        val image : ImageView = itemView.findViewById(R.id.iv_face)
    }
}