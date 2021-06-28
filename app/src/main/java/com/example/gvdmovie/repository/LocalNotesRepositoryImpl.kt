package com.example.gvdmovie.repository

import com.example.gvdmovie.room.NoteDao
import com.example.gvdmovie.room.NoteEntity

class LocalNotesRepositoryImpl (private val localDataSource: NoteDao) : LocalNotesRepository {

    override fun getAllNotes(movieId: Int): List<NoteEntity> {
        return localDataSource.getDataByMovieId(movieId.toString())
    }

    override fun saveEntity(noteEntity: NoteEntity) {
        localDataSource.insert(noteEntity)
    }

}
