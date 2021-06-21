package com.example.gvdmovie.repository

import com.example.gvdmovie.BuildConfig
import com.example.gvdmovie.model.MovieDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = BuildConfig.MOVIE_API_KEY

class RemoteDataSource {

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(MovieApi::class.java)

    fun getMovieDetails(id: String, lang: String, callback: Callback<MovieDTO>) {
        movieApi.getMovie(id, API_KEY, lang).enqueue(callback)
    }
}