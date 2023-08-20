package com.amir.batmanmovies.data.retrofit.request

import com.amir.batmanmovies.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDetailsRequest {

    @GET(".")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Response<Movie>
}