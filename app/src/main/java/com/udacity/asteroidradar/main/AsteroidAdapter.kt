package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.SingleAsteroidBinding

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {
    var data = listOf<Asteroid>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAsteroid = data[position]
        holder.bind(currentAsteroid)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(private val binding: SingleAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {

//        fun bind(item : Asteroid){
//            binding.tvAsteroidMainText.text = item.codename
//            binding.tvAsteroidDate.text = item.closeApproachDate
//
//            if (item.isPotentiallyHazardous) {
//                binding.ivFace.setImageResource(R.drawable.ic_status_potentially_hazardous)
//            } else {
//                binding.ivFace.setImageResource(R.drawable.ic_status_normal)
//            }
//        }

        fun bind(item : Asteroid){
            binding.asteroid = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleAsteroidBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}