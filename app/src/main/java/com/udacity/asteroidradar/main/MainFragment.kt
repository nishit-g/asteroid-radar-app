package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application

        val dataSource = AsteroidDatabase.getInstance(application)

        val viewModelFactory = MainViewModelFactory(dataSource, application)

        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        // Create an adapter
        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener { asteroid ->
            viewModel.onNavigateToAsteroidDetail(asteroid)
        })

        // Assign the adapter
        binding.asteroidRecycler.adapter = adapter

        // Assign the live data to the adapter's data
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                // If not using list adapter
//                adapter.data = it
            }
        })

        // Setup Picture of the day using Picasso
        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer {
            it?.let {

                Picasso.with(requireContext())
                        .load(it.url)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.placeholder_picture_of_day)
                        .into(binding.activityMainImageOfTheDay, object : Callback {
                            override fun onSuccess() {
                                // Offline cache hit
                                Log.v("Picasso", "Cache Hit")
                            }

                            override fun onError() {
                                Picasso.with(requireContext())
                                        .load(it.url)
                                        .into(binding.activityMainImageOfTheDay, object : Callback {
                                            override fun onSuccess() {
                                                //Online download
                                            }
                                            override fun onError() {
                                                Log.v("Picasso", "Could not fetch image")
                                            }
                                        })
                            }

                        })
            }
        })

        // navigate when the asteroid changes
        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.doneNavigatingToAsteroidDetail()
            }

        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilterSelection(
                when(item.itemId){
                    R.id.show_today_asteroids -> AsteroidApiFilter.LOAD_TODAY_ASTEROIDS
                    R.id.show_week_asteroids -> AsteroidApiFilter.LOAD_WEEK_ASTEROIDS
                    else -> AsteroidApiFilter.LOAD_SAVED_ASTEROIDS
                }
        )
        return true
    }
}
