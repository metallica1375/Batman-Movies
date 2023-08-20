package com.amir.batmanmovies.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amir.batmanmovies.data.model.Movie
import com.amir.batmanmovies.databinding.ActivityMovieDetailsBinding
import com.amir.batmanmovies.ui.viewmodel.MovieDetailsViewModel
import com.amir.batmanmovies.ui.viewmodelfactory.ContextBasedViewModelFactory
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var imdbId: String
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imdbId = intent.getStringExtra("id").toString()

        prepareViewModel()
        initiateMovieDetails()
    }

    private fun prepareViewModel() {
        val contextBasedViewModelFactory = ContextBasedViewModelFactory(this)
        movieDetailsViewModel =
            ViewModelProvider(this, contextBasedViewModelFactory)[MovieDetailsViewModel::class.java]
    }

    private fun setupBindings() {
        binding.overviewTextView.text = movie.Plot
        binding.rateTextView.text = movie.imdbRating
        binding.titleTextview.text = movie.Title
        binding.directorTextView.text = movie.Director
        binding.durationTextView.text = movie.Runtime
        binding.releaseDateTextView.text = movie.Released
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        Glide.with(this).load(movie.Poster).into(binding.moviePoster)
    }

    private fun initiateMovieDetails() {
        movieDetailsViewModel.returnMoviesList(imdbId).observe(this) { result ->
            result.onSuccess {
                movie = it!!
                setupBindings()
            }
            result.onFailure {
                binding.backButton.setOnClickListener {
                    onBackPressed()
                }
                toastMessage(it.message.toString())
            }

        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this@MovieDetailsActivity, message, Toast.LENGTH_SHORT).show()
    }
}