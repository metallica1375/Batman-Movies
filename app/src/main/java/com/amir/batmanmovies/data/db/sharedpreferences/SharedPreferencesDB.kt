package com.amir.batmanmovies.data.db.sharedpreferences

import android.content.Context
import com.amir.batmanmovies.data.db.MoviesDB
import com.amir.batmanmovies.data.model.Movies
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.gson.gson
import hu.autsoft.krate.gson.gsonPref

class SharedPreferencesDB(context: Context): SimpleKrate(context, "main.db"), MoviesDB {

    init {
        gson = GsonBuilder().setLenient().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()
    }

    override var Movies: List<Movies> by gsonPref<List<Movies>>(key = "movies_list").withDefault(listOf())

}