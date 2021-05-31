package com.example.gvdmovie.view.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.gvdmovie.R
import com.example.gvdmovie.databinding.ListFragmentBinding
import com.example.gvdmovie.viewmodel.AppState
import com.example.gvdmovie.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel
    private val adapter = ListFragmentAdapter()
    private var isDataSetRus: Boolean = true

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
        binding.listFragmentRecyclerView.adapter = adapter
        binding.listFragmentFAB.setOnClickListener { changeMovieDataSet() }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSourceRus()
    }

    private fun changeMovieDataSet() {
        if (isDataSetRus) {
            viewModel.getMovieFromLocalSourceRus()
            binding.listFragmentFAB.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            viewModel.getMovieFromLocalSourceWorld()
            binding.listFragmentFAB.setImageResource(R.drawable.ic_baseline_language_24)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                adapter.setMovie(appState.movieData)
            }
            is AppState.Loading -> {
                binding.listFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.listFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.listFragmentFAB, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.reload)) { viewModel.getMovieFromLocalSourceRus() }
                    .show()
            }
        }
    }

/*
    private fun openDetailFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, DetailFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
*/


    companion object {
        fun newInstance() = ListFragment()
    }
}