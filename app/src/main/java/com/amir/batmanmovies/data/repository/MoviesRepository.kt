package com.amir.batmanmovies.data.repository

import android.content.Context
import com.amir.batmanmovies.data.db.MoviesDB
import com.amir.batmanmovies.data.db.sharedpreferences.SharedPreferencesDB
import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.data.retrofit.client.MoviesListClient

class MoviesRepository(
    private val context: Context,
    private val moviesListClient: MoviesListClient = MoviesListClient(),
    private val moviesDB: MoviesDB = SharedPreferencesDB(context)
) {


    fun getMoviesList(): List<Movies> {
        return moviesDB.Movies
    }

    suspend fun fetchMoviesList(apiKey: String, filmName: String): Result<List<Movies>?> {
        val moviesList = moviesListClient.getMoviesList(apiKey, filmName)
        return try {
            if (moviesList.isSuccessful) {
                moviesDB.Movies = moviesList.body()!!.Search
                Result.success(moviesList.body()!!.Search)
            }
            Result.failure(Throwable(moviesList.message()))

        } catch (e: Exception) {
            Result.failure(Throwable("connection_failed. please retry!"))
        }
    }
}