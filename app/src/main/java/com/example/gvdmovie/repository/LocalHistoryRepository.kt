package com.example.gvdmovie.repository

import com.example.gvdmovie.model.Movie

interface LocalHistoryRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}