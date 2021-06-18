package com.example.gvdmovie.model

import android.os.Parcelable
import com.example.gvdmovie.utils.getDefaultCountry
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val country: Country = getDefaultCountry(),
    val title: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val tagline: String = "",
    val runtime: String = "",
    val poster: String = "",
) : Parcelable

fun getWorldMovies() = listOf(
        Movie(20312, Country("Канада", "CA"), "Трасса 60", "Interstate 60", "13/04/2002 (US)", "приключения, комедия, фэнтези", "1h 56m", ""),
        Movie(2044, Country("США", "US"), "Дом у озера", "", "", "", "", ""),
        Movie(399566, Country("США", "US"), "Годзилла против Конга", "", "", "", "", ""),
        Movie(438650, Country("США", "US"), "Снегоуборщик", "", "", "", "", ""),
        Movie(384018, Country("США", "US"), "Форсаж: Хоббс и Шоу", "", "", "", "", ""),
        Movie(793723, Country("США", "US"), "Часовой", "", "", "", "", ""),
)

fun getRussianMovies() = listOf(
        Movie(38329, Country("СССР", "SU"), "Приключения Электроника", "Приключения Электроника", "23/03/1980 (SU)", "фантастика, комедия, мюзикл", "216m", ""),
        Movie(471968, Country("Россия", "RU"), "Последний богатырь", "", "", "", "", ""),
        Movie(42779, Country("СССР", "SU"), "Семнадцать мгновений весны", "", "", "", "", ""),
        Movie(42279, Country("СССР", "SU"), "Самогонщики", "", "", "", "", ""),
        Movie(72614, Country("СССР", "SU"), "Аты-баты, шли солдаты...", "", "", "", "", ""),
        Movie(54970, Country("СССР", "SU"), "Весна на Заречной улице", "", "", "", "", ""),
)
