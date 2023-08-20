package com.amir.batmanmovies.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.data.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashViewModel(private val context: Context) : ViewModel() {

    //region Properties
    private val moviesRepository = MoviesRepository(context)
    //endregion

    private fun fetchMoviesList(): LiveData<Result<List<Movies>?>> {
        val liveData = MutableLiveData<Result<List<Movies>?>>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = moviesRepository.fetchMoviesList("3e974fca", "batman")
                liveData.postValue(result)
            } catch (e: Exception) {
                liveData.postValue(Result.failure(Throwable(e.message)))
            }

        }
        return liveData
    }

    private fun getMoviesList(): LiveData<Result<List<Movies>?>> {
        val liveData = MutableLiveData<Result<List<Movies>?>>()
            val result = moviesRepository.getMoviesList()
            if (result.isEmpty())
                liveData.postValue(Result.failure(Throwable("Internet connection needed to fetch data for the first time")))
            else
                liveData.postValue(Result.success(result))

        return liveData
    }

    fun returnMoviesList(): LiveData<Result<List<Movies>?>> {
        return if (checkForInternet())
            fetchMoviesList()
        else
            getMoviesList()
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