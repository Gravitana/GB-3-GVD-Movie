package com.example.gvdmovie.utils

import com.example.gvdmovie.model.Country
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.MovieDTO

const val DEFAULT_LANGUAGE = "ru-RU"

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