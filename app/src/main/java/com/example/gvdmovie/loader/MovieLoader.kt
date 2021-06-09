package com.example.gvdmovie.loader

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gvdmovie.BuildConfig
import com.example.gvdmovie.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val API_KEY = BuildConfig.MOVIE_API_KEY

@RequiresApi(Build.VERSION_CODES.N)
class MovieLoader(
    private val listener: MovieLoaderListener,
    private val id: Int,
    private val language: String = "ru-RU"
)  {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie() {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}&language=${language}")
            val handler = Handler()

            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
                    val movieDTO: MovieDTO =
                        Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)
                    handler.post { listener.onLoaded(movieDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }

}