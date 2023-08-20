package com.amir.batmanmovies.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.databinding.ActivityMoviesListBinding
import com.amir.batmanmovies.ui.adapter.MoviesListAdapter
import com.amir.batmanmovies.ui.utils.AdapterOnItemClickListener
import com.amir.batmanmovies.ui.viewmodel.MoviesListViewModel
import com.amir.batmanmovies.ui.viewmodelfactory.ContextBasedViewModelFactory

class MoviesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesListBinding
    private lateinit var moviesListViewModel: MoviesListViewModel
    private lateinit var adapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareViewModel()
        setupDarkMode()
        setupAdapter()
    }

    private fun setupDarkMode() {
        binding.nightModeSwitch.isChecked = moviesListViewModel.getAppSettings()
        binding.nightModeSwitch.setOnClickListener {
            if (binding.nightModeSwitch.isChecked) {
                moviesListViewModel.setAppSetting(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                moviesListViewModel.setAppSetting(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupAdapter() {
        adapter = MoviesListAdapter(
            moviesListViewModel.getMoviesList(),
            object : AdapterOnItemClickListener {
                override fun setOnItemListener(movie: Movies) {
                    val intent = Intent(this@MoviesListActivity, MovieDetailsActivity::class.java)
                    intent.putExtra("id", movie.imdbID)
                    startActivity(intent)
                }

            })

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.moviesRecyclerView.adapter = adapter
    }

    private fun prepareViewModel() {
        val contextBasedViewModelFactory = ContextBasedViewModelFactory(this)
        moviesListViewModel =
            ViewModelProvider(this, contextBasedViewModelFactory)[MoviesListViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        setupDarkMode()
    }

    override fun onStart() {
        super.onStart()
        setupDarkMode()
    }
}