package com.example.gvdmovie.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val movie_id: String,
    val title: String,
    val poster: String
)
