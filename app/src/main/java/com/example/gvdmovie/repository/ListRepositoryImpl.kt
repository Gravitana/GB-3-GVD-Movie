package com.example.gvdmovie.repository

import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.getRussianMovies
import com.example.gvdmovie.model.getWorldMovies

class ListRepositoryImpl : ListRepository {
    override fun getMovieFromServer() = Movie()

    override fun getMovieFromLocalStorageRus() = getRussianMovies()

    override fun getMovieFromLocalStorageWorld() = getWorldMovies()
}