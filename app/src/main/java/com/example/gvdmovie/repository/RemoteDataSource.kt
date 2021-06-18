package com.example.gvdmovie.repository

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataSource {

    fun getMovieDetails(requestLink: String, callback: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
//            header(REQUEST_API_KEY, BuildConfig.WEATHER_API_KEY)
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}