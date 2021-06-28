package com.example.gvdmovie.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(
    HistoryEntity::class,
    NoteEntity::class
), version = 3, exportSchema = false)
abstract class GvdMoviesDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun noteDao(): NoteDao
}
