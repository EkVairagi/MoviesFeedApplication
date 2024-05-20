package com.testing.moviesfeedapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.testing.moviesfeedapplication.R
import com.testing.moviesfeedapplication.adapter.MovieAdapter
import com.testing.moviesfeedapplication.databinding.FragmentPopularBinding
import com.testing.moviesfeedapplication.model.Result
import com.testing.moviesfeedapplication.ui.base.BaseFragment
import com.testing.moviesfeedapplication.viewmodel.PopularViewModel
import kotlinx.coroutines.launch


class PopularFragment :  BaseFragment<FragmentPopularBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPopularBinding
        get() = FragmentPopularBinding::inflate


    private  val viewModel: PopularViewModel by viewModels()
    private val data = ArrayList<Result>()
    private var page: String = "1"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }



    private fun initViews() {
        binding?.popular?.hasFixedSize()
        binding?.popular?.layoutManager = GridLayoutManager(context, 2)

        viewModel.fetchPopularMovies(page)
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movies.collect { results ->
                        results?.let {
                            data.clear()
                            data.addAll(it)
                            val popularAdapter = MovieAdapter(data) { id ->
                                onMovieClick(id)
                            }
                            binding?.popular?.adapter = popularAdapter
                        }
                    }
                }
                launch {
                    viewModel.error.collect { errorMessage ->
                        errorMessage?.let {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun onMovieClick(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(
            R.id.action_viewPagerFragment_to_detailsFragment,
            bundle
        )
    }


}