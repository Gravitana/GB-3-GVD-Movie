package com.example.gvdmovie.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val movie_id: String,
    val note: String
)
