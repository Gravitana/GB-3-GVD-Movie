package com.example.gvdmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gvdmovie.app.App.Companion.getHistoryDao
import com.example.gvdmovie.repository.LocalHistoryRepository
import com.example.gvdmovie.repository.LocalHistoryRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalHistoryRepository = LocalHistoryRepositoryImpl(getHistoryDao())
    ) : ViewModel() {

        fun getAllHistory() {
            historyLiveData.value = AppState.Loading
            historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
        }
}