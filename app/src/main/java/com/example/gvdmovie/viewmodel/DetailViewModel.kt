package com.example.gvdmovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gvdmovie.app.App.Companion.getHistoryDao
import com.example.gvdmovie.app.App.Companion.getNoteDao
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.MovieDTO
import com.example.gvdmovie.repository.*
import com.example.gvdmovie.room.NoteEntity
import com.example.gvdmovie.utils.convertDtoToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.Thread.sleep

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailViewModel(
    private val listRepositoryImpl: ListRepository = ListRepositoryImpl(),
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalHistoryRepository = LocalHistoryRepositoryImpl(getHistoryDao()),
    private val noteRepository: LocalNotesRepository = LocalNotesRepositoryImpl(getNoteDao())
) :
    ViewModel() {

    fun getMovieFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getMovieFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getMovieFromRemoteSource(id: String, lang: String) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getMovieDetailsFromServer(id, lang, callBack )
    }

    fun saveMovieToDB(movie: Movie) {
        historyRepository.saveEntity(movie)
    }

    fun getMovieNotes(movie: Movie) : List<NoteEntity> {
        return noteRepository.getAllNotes(movie.id)
    }

    fun saveNoteToDB(noteEntity: NoteEntity) {
        noteRepository.saveEntity(noteEntity)
    }

    private val callBack = object : Callback<MovieDTO> {

        override fun onResponse(call: Call<MovieDTO>?, response: Response<MovieDTO>) {
            val serverResponse: MovieDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDTO>?, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: MovieDTO): AppState {
            return if (serverResponse.id == null || serverResponse.title == null || serverResponse.poster_path == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(convertDtoToModel(serverResponse))
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
