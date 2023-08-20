package com.amir.batmanmovies.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.batmanmovies.data.model.Movie
import com.amir.batmanmovies.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val context: Context) : ViewModel() {

    private val moviesRepository = MoviesRepository(context)

    private fun fetchMovieDetails(imdbId: String): LiveData<Result<Movie?>> {
        val liveData = MutableLiveData<Result<Movie?>>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = moviesRepository.fetchMovieDetails("3e974fca", imdbId)
                liveData.postValue(result)
            } catch (e: Exception) {
                liveData.postValue(Result.failure(Throwable(e.message)))
            }

        }
        return liveData
    }

    private fun getMovieDetails(imdbId: String): LiveData<Result<Movie?>> {
        val liveData = MutableLiveData<Result<Movie?>>()
        val result = moviesRepository.getMovieDetails(imdbId)
        if (result == null)
            liveData.postValue(Result.failure(Throwable("Internet connection needed to fetch data for the first time")))
        else
            liveData.postValue(Result.success(result))

        return liveData
    }

    fun returnMoviesList(imdbId: String): LiveData<Result<Movie?>> {
        return if (checkForInternet())
            fetchMovieDetails(imdbId)
        else
            getMovieDetails(imdbId)
    }

    private fun checkForInternet(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}