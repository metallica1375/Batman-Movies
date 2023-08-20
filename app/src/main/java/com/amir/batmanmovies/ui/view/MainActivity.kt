package com.amir.batmanmovies.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.data.repository.MoviesRepository
import com.amir.batmanmovies.databinding.ActivityMainBinding
import com.amir.batmanmovies.ui.adapter.MoviesListAdapter
import com.amir.batmanmovies.ui.utils.AdapterOnItemClickListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MoviesListAdapter
    private lateinit var moviesRepository: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesRepository = MoviesRepository(this)
        adapter = MoviesListAdapter(moviesRepository.getMoviesList(), object : AdapterOnItemClickListener{
            override fun setOnItemListener(movie: Movies) {
                val mv = movie
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("id", movie.imdbID)
                startActivity(intent)
            }

        })

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.moviesRecyclerView.adapter = adapter

    }
}