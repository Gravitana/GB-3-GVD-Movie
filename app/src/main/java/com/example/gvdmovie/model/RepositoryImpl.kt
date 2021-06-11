package com.example.gvdmovie.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer() = Movie()

    override fun getMovieFromLocalStorageRus() = getRussianMovies()

    override fun getMovieFromLocalStorageWorld() = getWorldMovies()
}