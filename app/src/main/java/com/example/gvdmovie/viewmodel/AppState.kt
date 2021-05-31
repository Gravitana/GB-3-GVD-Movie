package com.example.gvdmovie.viewmodel

import com.example.gvdmovie.model.Movie

sealed class AppState  {
    data class Success(val movieData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
