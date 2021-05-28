package com.example.gvdmovie.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gvdmovie.AppState
import com.example.gvdmovie.model.Repository
import com.example.gvdmovie.model.RepositoryImpl
import java.lang.Thread.sleep

class DetailViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMovieFromLocalSource() = getDataFromLocalSource()

    fun getMovieFromRemoteSource() = getDataFromLocalSource() // TODO

    private fun getDataFromLocalSource() {

        liveDataToObserve.value = AppState.Loading

        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMovieFromLocalStorage()))
        }.start()
    }
}