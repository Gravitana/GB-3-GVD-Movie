package com.example.gvdmovie.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorageRus(): List<Movie> {
        return getRussianMovies()
    }

    override fun getMovieFromLocalStorageWorld(): List<Movie> {
        return getWorldMovies()
    }
}