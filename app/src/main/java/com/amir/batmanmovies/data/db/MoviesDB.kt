package com.amir.batmanmovies.data.db

import com.amir.batmanmovies.data.model.Movie
import com.amir.batmanmovies.data.model.Movies

interface MoviesDB {

    var movies: List<Movies>
    var moviesListDetails: MutableMap<String, Movie>
}