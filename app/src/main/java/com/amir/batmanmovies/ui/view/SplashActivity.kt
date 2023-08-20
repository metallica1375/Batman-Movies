package com.amir.batmanmovies.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amir.batmanmovies.databinding.ActivitySplashBinding
import com.amir.batmanmovies.ui.viewmodel.SplashViewModel
import com.amir.batmanmovies.ui.viewmodelfactory.ContextBasedViewModelFactory

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareViewModel()
        setViewBindings()
        getMoviesList()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    }

    private fun setViewBindings() {
        binding.retryButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.retryButton.visibility = View.GONE
            getMoviesList()
        }
    }

    private fun getMoviesList() {
        splashViewModel.returnMoviesList().observe(this) { result ->
            result.onSuccess {
                binding.progressBar.visibility = View.GONE
                startHomeActivity()
                finish()
            }
            result.onFailure {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.VISIBLE
                toastMessage(it.message.toString())
            }

        }
    }

    private fun startHomeActivity()  {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun prepareViewModel() {
        val contextBasedViewModelFactory = ContextBasedViewModelFactory(this)
        splashViewModel = ViewModelProvider(this, contextBasedViewModelFactory)[SplashViewModel::class.java]
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this@SplashActivity, message, Toast.LENGTH_SHORT).show()
    }
}