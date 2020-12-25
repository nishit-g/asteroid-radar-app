package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {asteroid ->
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

        // navigate when the asteroid changes
        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner, Observer {asteroid ->
            asteroid?.let{
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
        return true
    }
}
