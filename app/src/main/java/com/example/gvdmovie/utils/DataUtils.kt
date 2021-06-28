package com.example.gvdmovie.utils

import com.example.gvdmovie.model.Country
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.MovieDTO
import com.example.gvdmovie.room.HistoryEntity

const val DEFAULT_LANGUAGE = "ru-RU"
const val WITH_ADULT_KEY = "LIST_MOVIES_WITH_ADULT_KEY"

fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
    return listOf(Movie(
        movieDTO.id!!,
        getDefaultCountry(),
        movieDTO.title!!,
        movieDTO.original_title!!,
        movieDTO.release_date!!,
        movieDTO.tagline!!,
        movieDTO.runtime!!,
        movieDTO.poster_path!!,

    ))
}

fun getDefaultCountry() = Country("Россия", "RU")

fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): List<Movie> {
    return entityList.map {
        Movie(it.movie_id.toInt(), getDefaultCountry(), it.title, "", "", "", "", it.poster)
    }
}

fun convertMovieToEntity(movie: Movie): HistoryEntity {
    return HistoryEntity(0, movie.id.toString(), movie.title, movie.poster)
}