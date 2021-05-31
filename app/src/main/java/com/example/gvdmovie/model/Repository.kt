package com.example.gvdmovie.model

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorageRus(): List<Movie>
    fun getMovieFromLocalStorageWorld(): List<Movie>
}