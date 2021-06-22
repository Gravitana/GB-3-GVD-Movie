package com.example.gvdmovie.repository

import com.example.gvdmovie.room.NoteEntity

interface LocalNotesRepository {
    fun getAllNotes(movieId: Int): List<NoteEntity>
    fun saveEntity(noteEntity: NoteEntity)
}