package com.example.gvdmovie.app

import android.app.Application
import androidx.room.Room
import com.example.gvdmovie.room.HistoryDao
import com.example.gvdmovie.room.GvdMoviesDataBase
import com.example.gvdmovie.room.NoteDao

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: GvdMoviesDataBase? = null
        private const val DB_NAME = "GvdMovies.db"

        fun getHistoryDao(): HistoryDao {
            initDb()
            return db!!.historyDao()
        }

        fun getNoteDao(): NoteDao {
            initDb()
            return db!!.noteDao()
        }

        private fun initDb() {
            if (db == null) {
                synchronized(GvdMoviesDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            GvdMoviesDataBase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
        }
    }
}
