package com.amir.batmanmovies.data.repository

import android.content.Context
import com.amir.batmanmovies.data.db.AppSettingsDB
import com.amir.batmanmovies.data.db.MoviesDB
import com.amir.batmanmovies.data.db.sharedpreferences.SharedPreferencesDB
import com.amir.batmanmovies.data.model.Movie
import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.data.retrofit.client.MovieDetailsClient
import com.amir.batmanmovies.data.retrofit.client.MoviesListClient

class MoviesRepository(
    private val context: Context,
    private val moviesListClient: MoviesListClient = MoviesListClient(),
    private val movieDetailsClient: MovieDetailsClient = MovieDetailsClient(),
    private val moviesDB: MoviesDB = SharedPreferencesDB(context),
    private val appSettingsDB: AppSettingsDB = SharedPreferencesDB(context)
) {

    fun getMoviesList(): List<Movies> {
        return moviesDB.movies
    }

    suspend fun fetchMoviesList(apiKey: String, filmName: String): Result<List<Movies>?> {
        val moviesList = moviesListClient.getMoviesList(apiKey, filmName)
        return try {
            if (moviesList.isSuccessful) {
                moviesDB.movies = moviesList.body()!!.Search
                Result.success(moviesList.body()!!.Search)
            } else {
                if (moviesDB.movies.isEmpty())
                    Result.failure(Throwable("Internet connection needed to fetch data for the first time"))
                else
                    Result.success(moviesDB.movies)
            }

        } catch (e: Exception) {
            if (moviesDB.movies.isEmpty())
                Result.failure(Throwable("connection_failed. please retry!"))
            else
                Result.success(moviesDB.movies)
        }
    }

    suspend fun fetchMovieDetails(apiKey: String, imdbId: String): Result<Movie?> {
        val movie = movieDetailsClient.getMovieDetails(apiKey, imdbId)
        return try {
            if (movie.isSuccessful) {
                if (moviesDB.movies.isEmpty()) {
                    val tempMap = mutableMapOf<String, Movie>()
                    tempMap[imdbId] = movie.body()!!
                    moviesDB.moviesListDetails = tempMap
                } else {
                    val tempMap = moviesDB.moviesListDetails
                    tempMap[imdbId] = movie.body()!!
                    moviesDB.moviesListDetails = tempMap
                }
                Result.success(movie.body())
            } else {
                if (moviesDB.moviesListDetails[imdbId] != null) {
                    Result.success(moviesDB.moviesListDetails[imdbId])
                } else
                    Result.failure(Throwable("Internet connection needed to fetch data for the first time"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("connection_failed. please retry!"))
        }
    }

    fun getMovieDetails(imdbId: String): Movie? {
        return moviesDB.moviesListDetails[imdbId]
    }

    fun getAppSettings() = appSettingsDB.darkMode
    fun setAppSettings(state: Boolean) {
        appSettingsDB.darkMode = state
    }
}