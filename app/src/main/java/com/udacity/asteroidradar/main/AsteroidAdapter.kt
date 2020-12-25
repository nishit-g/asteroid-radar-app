package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.SingleAsteroidBinding


// TODO : Implement DiffUtils

class AsteroidAdapter(private val clickListener : AsteroidClickListener) : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {

    // If the data is not in the list format of we don't want to use list adapter
//    var data = mutableListOf<Asteroid>()
//        set(newData){
//            val diffCallback = AsteroidLongDiffCallback(data, newData)
//            val diffResult = DiffUtil.calculateDiff(diffCallback)
//            data.clear()
//            data.addAll(newData)
//            diffResult.dispatchUpdatesTo(this)
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAsteroid = getItem(position)
        holder.bind(clickListener, currentAsteroid)
    }



    // ViewHolder Class
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

        fun bind(clickListener: AsteroidClickListener, item : Asteroid){
            binding.asteroid = item
            binding.clickListener = clickListener
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

    // Click Listener
    class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit){
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

    // DiffUtils Class -> If data is in list format
    class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

    }


    // If the give data is not in list format
//    class AsteroidLongDiffCallback(private val oldList: List<Asteroid>, private val newList: List<Asteroid>) : DiffUtil.Callback() {
//
//        override fun getOldListSize(): Int = oldList.size
//
//        override fun getNewListSize(): Int = newList.size
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return oldList[oldItemPosition].id == newList[newItemPosition].id
//        }
//
//        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
//           return oldList[oldPosition] == newList[newPosition]
//        }
//
//        @Nullable
//        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
//            return super.getChangePayload(oldPosition, newPosition)
//        }
//    }
}