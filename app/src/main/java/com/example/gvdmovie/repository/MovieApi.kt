package com.example.gvdmovie.repository

import com.example.gvdmovie.model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/{id}")
    fun getMovie(
        @Path("id") id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<MovieDTO>
}
