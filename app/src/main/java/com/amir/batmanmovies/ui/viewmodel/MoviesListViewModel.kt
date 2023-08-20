package com.amir.batmanmovies.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amir.batmanmovies.data.repository.MoviesRepository

class MoviesListViewModel(context: Context) : ViewModel() {

    private val moviesRepository = MoviesRepository(context)

    fun getMoviesList() = moviesRepository.getMoviesList()

    fun getAppSettings() = moviesRepository.getAppSettings()
    fun setAppSetting(state: Boolean) {
        moviesRepository.setAppSettings(state)
    }

}