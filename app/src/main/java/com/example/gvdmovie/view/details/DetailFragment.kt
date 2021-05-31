package com.example.gvdmovie.view.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.gvdmovie.viewmodel.AppState
import com.example.gvdmovie.databinding.DetailFragmentBinding
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

/*
    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message.text = "Подробная информация о фильме"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                binding.loadingLayout.visibility = View.GONE
//                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
                setData(movieData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovieFromLocalSource() }
                    .show()
            }
        }
    }

    private fun setData(movieData: Movie) {
        binding.movieTitle.text = movieData.title
        binding.movieOriginalTitle.text = movieData.originalTitle
        binding.movieReleaseDate.text = movieData.releaseDate
        binding.movieTagline.text = movieData.tagline
        binding.movieRuntime.text = movieData.runtime
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        Обязательно обнуляем _binding в onDestroyView, чтобы избежать утечек и не желаемого поведения.
//        В Activity ничего похожего делать не требуется
        _binding = null
    }
*/

}