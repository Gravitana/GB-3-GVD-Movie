package com.example.gvdmovie.repository

import com.example.gvdmovie.model.Movie

interface ListRepository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorageRus(): List<Movie>
    fun getMovieFromLocalStorageWorld(): List<Movie>
}