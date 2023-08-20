package com.amir.batmanmovies.data.retrofit.request

import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.data.model.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesListRequest {

    @GET(".")
    suspend fun getMoviesList(@Query("apikey") apiKey: String, @Query("s") filmName: String): Response<Search>

}