package com.example.gvdmovie.room

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntity")
    fun all(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE movie_id LIKE :movie_id")
    fun getDataByMovieId(movie_id: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: NoteEntity)

    @Update
    fun update(entity: NoteEntity)

    @Delete
    fun delete(entity: NoteEntity)

}