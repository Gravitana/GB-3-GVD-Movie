package com.example.gvdmovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gvdmovie.AppState
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMovie() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {

        liveDataToObserve.value = AppState.Loading

        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(Any()))
        }.start()
    }
}
