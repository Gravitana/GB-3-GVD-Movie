package com.example.gvdmovie.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(

    @PrimaryKey(autoGenerate = false)
    val movie_id: String,
    val note: String
)
