package com.amir.batmanmovies.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amir.batmanmovies.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieImdbId.text = intent.getStringExtra("id")
    }
}