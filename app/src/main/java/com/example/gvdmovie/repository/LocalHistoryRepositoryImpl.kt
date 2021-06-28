package com.example.gvdmovie.repository

import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.room.HistoryDao
import com.example.gvdmovie.utils.convertHistoryEntityToMovie
import com.example.gvdmovie.utils.convertMovieToEntity

class LocalHistoryRepositoryImpl (private val localDataSource: HistoryDao) :
    LocalHistoryRepository {

    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToEntity(movie))
    }
}
