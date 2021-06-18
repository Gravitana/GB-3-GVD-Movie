package com.example.gvdmovie.repository

import com.example.gvdmovie.model.MovieDTO

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getMovieDetailsFromServer(
        id: String,
        lang: String,
        callback: retrofit2.Callback<MovieDTO>
    ) {
        remoteDataSource.getMovieDetails(id, lang, callback)
    }
}
