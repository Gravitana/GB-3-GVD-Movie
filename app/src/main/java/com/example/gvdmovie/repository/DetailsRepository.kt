package com.example.gvdmovie.repository

import com.example.gvdmovie.model.MovieDTO

interface DetailsRepository {
    fun getMovieDetailsFromServer(
        id: String,
        lang: String,
        callback: retrofit2.Callback<MovieDTO>
    )
}