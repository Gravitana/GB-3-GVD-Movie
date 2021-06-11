package com.example.gvdmovie.model

data class MovieDTO(
    val id: Int?,
//    val country: Country = getDefaultCountry(),
    val title: String?,
    val original_title: String?,
    val release_date: String?,
    val tagline: String?,
    val runtime: String?,
    val poster_path: String?,
)
