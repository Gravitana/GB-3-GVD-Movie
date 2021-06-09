package com.example.gvdmovie.view.details

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.gvdmovie.databinding.DetailFragmentBinding
import com.example.gvdmovie.loader.MovieLoader
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.model.MovieDTO

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieBundle: Movie

    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {

            override fun onLoaded(movieDTO: MovieDTO) {
                displayMovie(movieDTO)
            }

            override fun onFailed(throwable: Throwable) {
                // TODO Обработка ошибки
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Movie()

        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val loader = MovieLoader(onLoadListener, movieBundle.id)
        loader.loadMovie()
    }

    private fun displayMovie(movieDTO: MovieDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE

            message.text = "Подробная информация о фильме"

            movieTitle.text = movieDTO.title
            movieOriginalTitle.text = movieDTO.original_title
            movieReleaseDate.text = movieDTO.release_date
            movieTagline.text = movieDTO.tagline
            movieRuntime.text = movieDTO.runtime
        }
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
