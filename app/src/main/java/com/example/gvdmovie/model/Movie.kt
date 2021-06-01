package com.example.gvdmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val country: Country = getDefaultCountry(),
    val title: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val tagline: String = "",
    val runtime: String = "",
    val poster: String = "",
) : Parcelable

fun getDefaultCountry() = Country("Россия", "ru")

fun getWorldMovies(): List<Movie> {
    return listOf(
        Movie(Country("Канада", "CA"), "Трасса 60", "Interstate 60", "13/04/2002 (US)", "приключения, комедия, фэнтези", "1h 56m", ""),
        Movie(Country("США", "US"), "Американский фильм", "", "", "", "", ""),
        Movie(Country("Япония", "JP"), "Японский фильм", "", "", "", "", ""),
        Movie(Country("США", "US"), "Американский фильм 2", "", "", "", "", ""),
        Movie(Country("Италия", "IT"), "Кино из Италии", "", "", "", "", ""),
        Movie(Country("Франция", "FR"), "Французское кино", "", "", "", "", ""),
    )
}

fun getRussianMovies(): List<Movie> {
    return listOf(
        Movie(Country("СССР", "SU"), "Приключения Электроника", "Приключения Электроника", "23/03/1980 (SU)", "фантастика, комедия, мюзикл", "216m", ""),
        Movie(Country("Россия", "RU"), "Российский фильм", "", "", "", "", ""),
        Movie(Country("Россия", "RU"), "Ещё один Российский фильм", "", "", "", "", ""),
        Movie(Country("СССР", "SU"), "Классика из СССР", "", "", "", "", ""),
        Movie(Country("СССР", "SU"), "Советское кино", "", "", "", "", ""),
        Movie(Country("СССР", "SU"), "Кино из СССР", "", "", "", "", ""),
    )
}
