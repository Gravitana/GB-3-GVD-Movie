package com.example.gvdmovie.view.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gvdmovie.R
import com.example.gvdmovie.databinding.ListFragmentBinding
import com.example.gvdmovie.model.Movie
import com.example.gvdmovie.view.details.DetailFragment
import com.example.gvdmovie.view.showSnackBar
import com.example.gvdmovie.viewmodel.AppState
import com.example.gvdmovie.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }
    private var isDataSetRus: Boolean = true

    private val adapter = ListFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .add(R.id.container, DetailFragment.newInstance(Bundle().apply { putParcelable(DetailFragment.BUNDLE_EXTRA, movie) }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listFragmentRecyclerView.adapter = adapter
        listFragmentFAB.setOnClickListener { changeMovieDataSet() }
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieFromLocalSourceRus()
    }

    private fun changeMovieDataSet() {
        isDataSetRus = !isDataSetRus

        if (isDataSetRus) {
            viewModel.getMovieFromLocalSourceRus()
            binding.listFragmentFAB.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            viewModel.getMovieFromLocalSourceWorld()
            binding.listFragmentFAB.setImageResource(R.drawable.ic_baseline_language_24)
        }
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                listFragmentLoadingLayout.visibility = View.GONE
                adapter.setMovie(appState.movieData)
            }
            is AppState.Loading -> {
                listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                listFragmentLoadingLayout.visibility = View.GONE
                listFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getMovieFromLocalSourceRus() })
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}