package com.amir.batmanmovies.data.retrofit

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MoviesApi {

    companion object{

        //region Properties
        private const val BASE_URL = "https://www.omdbapi.com/"
        //endregion

        //region Methods
        fun getClient(): Retrofit {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient: OkHttpClient =
                OkHttpClient().newBuilder().addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS).build()

            val gson: Gson = GsonBuilder().setLenient().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()

        }
        //endregion
    }
}