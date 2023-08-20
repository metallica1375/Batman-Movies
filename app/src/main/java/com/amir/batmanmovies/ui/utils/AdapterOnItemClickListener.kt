package com.amir.batmanmovies.ui.utils

import com.amir.batmanmovies.data.model.Movies

interface AdapterOnItemClickListener {
    fun setOnItemListener(movie: Movies)
}