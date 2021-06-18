package com.example.gvdmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gvdmovie.model.MovieDTO
import com.example.gvdmovie.repository.ListRepository
import com.example.gvdmovie.repository.ListRepositoryImpl
import com.example.gvdmovie.repository.DetailsRepository
import com.example.gvdmovie.repository.DetailsRepositoryImpl
import com.example.gvdmovie.repository.RemoteDataSource
import com.example.gvdmovie.utils.convertDtoToModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.Thread.sleep

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailViewModel(
    private val listRepositoryImpl: ListRepository = ListRepositoryImpl(),
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource())
) :
    ViewModel() {

    fun getLiveData() = detailsLiveData

    fun getMovieFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getMovieFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getMovieFromRemoteSource(requestLink: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(requestLink, callBack)
    }


    private val callBack = object : Callback {

        @Throws(IOException::class)
        override fun onResponse(call: Call?, response: Response) {
            val serverResponse: String? = response.body()?.string()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call?, e: IOException?) {
            detailsLiveData.postValue(AppState.Error(Throwable(e?.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: String): AppState {
            val movieDTO: MovieDTO =
                Gson().fromJson(serverResponse, MovieDTO::class.java)

            return if (movieDTO.id == null || movieDTO.title == null || movieDTO.poster_path == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(movieDTO))
            }
        }
    }

    private fun getDataFromLocalSource(isRussian: Boolean) {

        detailsLiveData.value = AppState.Loading

        val isMovieLoaded = true//Random().nextBoolean()

        Thread {
            sleep(1000)
            detailsLiveData.postValue(
                if (isMovieLoaded) AppState.Success(
                    if (isRussian) listRepositoryImpl.getMovieFromLocalStorageRus()
                    else listRepositoryImpl.getMovieFromLocalStorageWorld())
                else AppState.Error(Exception("Неудачная загрузка"))
            )
        }.start()
    }
}
