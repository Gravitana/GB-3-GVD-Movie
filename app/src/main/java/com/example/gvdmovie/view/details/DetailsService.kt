package com.example.gvdmovie.view.details

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gvdmovie.BuildConfig
import com.example.gvdmovie.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val ID_EXTRA = "Movie id"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

private const val API_KEY = BuildConfig.MOVIE_API_KEY

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val language: String = "ru-RU"

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(ID_EXTRA, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadMovie(id.toString())
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(id: String) {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/${id}?api_key=$API_KEY&language=${language}")

            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                }

                val movieDTO: MovieDTO =
                    Gson().fromJson(getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))), MovieDTO::class.java)
                onResponse(movieDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieDTO: MovieDTO) {
        if (movieDTO == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(
                movieDTO.id,
                movieDTO.title,
                movieDTO.original_title,
                movieDTO.release_date,
                movieDTO.tagline,
                movieDTO.runtime,
                movieDTO.poster_path
            )
        }
    }

    private fun onSuccessResponse(
        id: Int?,
        title: String?,
        original_title: String?,
        release_date: String?,
        tagline: String?,
        runtime: String?,
        poster_path: String?,
    ) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_ID_EXTRA, id)
        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, title)
        broadcastIntent.putExtra(DETAILS_ORIGINAL_TITLE_EXTRA, original_title)
        broadcastIntent.putExtra(DETAILS_RELEASE_DATE_EXTRA, release_date)
        broadcastIntent.putExtra(DETAILS_TAGLINE_EXTRA, tagline)
        broadcastIntent.putExtra(DETAILS_RUNTIME_EXTRA, runtime)
        broadcastIntent.putExtra(DETAILS_POSTER_PATH_EXTRA, poster_path)

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)

    }
}