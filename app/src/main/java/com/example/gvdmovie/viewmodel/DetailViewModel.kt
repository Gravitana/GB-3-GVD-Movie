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

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    fun getMovieFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {

        liveDataToObserve.value = AppState.Loading

        val isMovieLoaded = Random().nextBoolean()

        Thread {
            sleep(2000)
            liveDataToObserve.postValue(
                if (isMovieLoaded) AppState.Success(repositoryImpl.getMovieFromLocalStorage())
                else AppState.Error(Exception("Неудачная загрузка"))
            )
        }.start()
    }
}