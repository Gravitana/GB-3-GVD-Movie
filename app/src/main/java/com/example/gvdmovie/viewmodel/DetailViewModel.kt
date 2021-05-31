package com.example.gvdmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gvdmovie.model.Repository
import com.example.gvdmovie.model.RepositoryImpl
import java.lang.Exception
import java.lang.Thread.sleep
import java.util.*

class DetailViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMovieFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getMovieFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getMovieFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {

        liveDataToObserve.value = AppState.Loading

        val isMovieLoaded = Random().nextBoolean()

        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                if (isMovieLoaded) AppState.Success(
                    if (isRussian) repositoryImpl.getMovieFromLocalStorageRus()
                    else repositoryImpl.getMovieFromLocalStorageWorld())
                else AppState.Error(Exception("Неудачная загрузка"))
            )
        }.start()
    }
}