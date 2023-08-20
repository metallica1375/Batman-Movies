package com.amir.batmanmovies.data.retrofit.client

import com.amir.batmanmovies.data.model.Movie
import com.amir.batmanmovies.data.retrofit.MoviesApi
import com.amir.batmanmovies.data.retrofit.request.MovieDetailsRequest
import retrofit2.Response

class MovieDetailsClient {

    private val apiRequest: MovieDetailsRequest =
        MoviesApi.getClient().create(MovieDetailsRequest::class.java)

    suspend fun getMovieDetails(apiKey: String, imdbId: String): Response<Movie> {
        return apiRequest.getMovieDetails(apiKey, imdbId)
    }

}