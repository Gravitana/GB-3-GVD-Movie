package com.example.gvdmovie

import com.example.gvdmovie.model.Movie

sealed class AppState  {
    data class Success(val movieData: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
