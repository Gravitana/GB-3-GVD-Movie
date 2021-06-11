package com.example.gvdmovie.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gvdmovie.databinding.DetailFragmentBinding
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.MovieDTO
import com.squareup.picasso.Picasso

const val POSTER_WIDTH = "w500"

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"

const val DETAILS_ID_EXTRA = "MOVIE ID"
const val DETAILS_TITLE_EXTRA = "MOVIE TITLE"
const val DETAILS_ORIGINAL_TITLE_EXTRA = "MOVIE ORIGINAL TITLE"
const val DETAILS_RELEASE_DATE_EXTRA = "MOVIE RELEASE DATE"
const val DETAILS_TAGLINE_EXTRA = "MOVIE TAGLINE"
const val DETAILS_RUNTIME_EXTRA = "MOVIE RUNTIME"
const val DETAILS_POSTER_PATH_EXTRA = "MOVIE POSTER PATH"

const val ID_INVALID = 0
const val STRING_INVALID = ""

private const val PROCESS_ERROR = "Обработка ошибки"

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieBundle: Movie

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    MovieDTO(
                        intent.getIntExtra(DETAILS_ID_EXTRA, ID_INVALID),
                        intent.getStringExtra(DETAILS_TITLE_EXTRA),
                        intent.getStringExtra(DETAILS_ORIGINAL_TITLE_EXTRA),
                        intent.getStringExtra(DETAILS_RELEASE_DATE_EXTRA),
                        intent.getStringExtra(DETAILS_TAGLINE_EXTRA),
                        intent.getStringExtra(DETAILS_RUNTIME_EXTRA),
                        intent.getStringExtra(DETAILS_POSTER_PATH_EXTRA),
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

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
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE

        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(
                    ID_EXTRA,
                    movieBundle.id
                )
//                putExtra(
//                    LONGITUDE_EXTRA,
//                    weatherBundle.city.lon
//                )
            })
        }
    }

    private fun renderData(movieDTO: MovieDTO) {
        binding.mainView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE

        val id = movieDTO.id
        val title = movieDTO.title ?: STRING_INVALID
        val original_title = movieDTO.original_title ?: STRING_INVALID
        val release_date = movieDTO.release_date ?: STRING_INVALID
        val tagline = movieDTO.tagline ?: STRING_INVALID
        val runtime = movieDTO.runtime ?: STRING_INVALID
        val poster_path = movieDTO.poster_path ?: STRING_INVALID

        if (id == ID_INVALID) {
            TODO(PROCESS_ERROR)
        } else {
            with(binding) {
                message.text = "Подробная информация о фильме"

                movieTitle.text = title
                movieOriginalTitle.text = original_title
                movieReleaseDate.text = release_date
                movieTagline.text = tagline
                movieRuntime.text = runtime

                if (poster_path != STRING_INVALID) {
                    Picasso
                        .get()
                        .load("https://image.tmdb.org/t/p/${POSTER_WIDTH}${poster_path}")
                        .into(movieImage)
                }
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
