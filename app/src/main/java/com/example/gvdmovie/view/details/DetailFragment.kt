package com.example.gvdmovie.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gvdmovie.BuildConfig
import com.example.gvdmovie.databinding.DetailFragmentBinding
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.MovieDTO
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_fragment.*
import okhttp3.*
import java.io.IOException

const val POSTER_WIDTH = "w500"
const val STRING_EMPTY = ""

private const val PROCESS_ERROR = "Обработка ошибки"
private const val MAIN_LINK = "https://api.themoviedb.org/3/movie/"
private const val API_KEY = BuildConfig.MOVIE_API_KEY
private const val LANGUAGE = "ru-RU"

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieBundle: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Movie()
        getMovie()
    }

    private fun getMovie() {
        mainView.visibility = View.GONE
        loadingLayout.visibility = View.VISIBLE

        val client = OkHttpClient() // Клиент
        val builder: Request.Builder = Request.Builder() // Создаём строителя запроса

        builder.url(MAIN_LINK + "${movieBundle.id}?api_key=${API_KEY}&language=${LANGUAGE}") // Формируем URL

        val request: Request = builder.build() // Создаём запрос
        val call: Call = client.newCall(request) // Ставим запрос в очередь и отправляем
        call.enqueue(object : Callback {

            val handler: Handler = Handler()

            // Вызывается, если ответ от сервера пришёл
            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                val serverResponse: String? = response.body()?.string()
                // Синхронизируем поток с потоком UI
                if (response.isSuccessful && serverResponse != null) {
                    handler.post {
                        renderData(Gson().fromJson(serverResponse, MovieDTO::class.java))
                    }
                } else {
                    TODO(PROCESS_ERROR)
                }
            }

            // Вызывается при сбое в процессе запроса на сервер
            override fun onFailure(call: Call?, e: IOException?) {
                TODO(PROCESS_ERROR)
            }
        })

    }

    private fun renderData(movieDTO: MovieDTO) {
        mainView.visibility = View.VISIBLE
        loadingLayout.visibility = View.GONE

        if (movieDTO.id == null || movieDTO.title == null || movieDTO.poster_path == null) {
            TODO(PROCESS_ERROR)
        } else {
            with(binding) {
                message.text = "Подробная информация о фильме"

                movieTitle.text = movieDTO.title
                movieOriginalTitle.text = movieDTO.original_title ?: STRING_EMPTY
                movieReleaseDate.text = movieDTO.release_date ?: STRING_EMPTY
                movieTagline.text = movieDTO.tagline ?: STRING_EMPTY
                movieRuntime.text = movieDTO.runtime ?: STRING_EMPTY

                Picasso
                    .get()
                    .load("https://image.tmdb.org/t/p/${POSTER_WIDTH}${movieDTO.poster_path}")
                    .into(movieImage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
