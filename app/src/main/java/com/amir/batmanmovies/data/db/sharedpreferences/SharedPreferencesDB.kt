package com.amir.batmanmovies.data.db.sharedpreferences

import android.content.Context
import com.amir.batmanmovies.data.db.AppSettingsDB
import com.amir.batmanmovies.data.db.MoviesDB
import com.amir.batmanmovies.data.model.Movie
import com.amir.batmanmovies.data.model.Movies
import com.google.gson.GsonBuilder
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.gson.gson
import hu.autsoft.krate.gson.gsonPref

class SharedPreferencesDB(context: Context) : SimpleKrate(context, "main.db"), MoviesDB, AppSettingsDB {

    init {
        gson = GsonBuilder().setLenient().create()
    }

    override var movies: List<Movies> by gsonPref<List<Movies>>(key = "movies_list").withDefault(
        listOf()
    )
    override var moviesListDetails: MutableMap<String, Movie> by gsonPref<MutableMap<String, Movie>>(
        key = "movies_details"
    ).withDefault(
        mutableMapOf()
    )
    override var darkMode: Boolean by booleanPref(key = "dark_mode").withDefault(true)

}