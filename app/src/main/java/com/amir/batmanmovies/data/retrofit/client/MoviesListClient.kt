package com.amir.batmanmovies.data.retrofit.client

import com.amir.batmanmovies.data.model.Search
import com.amir.batmanmovies.data.retrofit.MoviesApi
import com.amir.batmanmovies.data.retrofit.request.MoviesListRequest
import retrofit2.Response

class MoviesListClient {

    private val apiRequest : MoviesListRequest = MoviesApi.getClient().create(MoviesListRequest::class.java)

    suspend fun getMoviesList(apiKey: String, filmName: String): Response<Search> {
        return apiRequest.getMoviesList(apiKey, filmName)
    }
}