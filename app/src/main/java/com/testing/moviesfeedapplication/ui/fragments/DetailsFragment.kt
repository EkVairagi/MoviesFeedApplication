package com.testing.moviesfeedapplication.ui.fragments

import android.app.ProgressDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.testing.moviesfeedapplication.databinding.FragmentDetailsBinding
import com.testing.moviesfeedapplication.ui.base.BaseFragment
import com.testing.moviesfeedapplication.utils.DateUtils
import com.testing.moviesfeedapplication.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject


class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var progressDialog: ProgressDialog
    var id: String = ""
    private var genre: String? = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initViews() {
        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        id = arguments?.getString("id") ?: ""
        detailsApi()
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collect { result ->
                    progressDialog.dismiss()
                    result?.fold(
                        onSuccess = { res ->
                            updateUI(res)
                        },
                        onFailure = { e ->
                            Toast.makeText(requireContext(), "Failed to load details", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    private fun detailsApi() {
        progressDialog = ProgressDialog.show(requireContext(), "", "Loading")
        viewModel.fetchMovieDetails(id)
    }

    private fun updateUI(res: JSONObject) {
        Log.d(ContentValues.TAG, res.toString())
        val arr = res.optJSONArray("genres")
        var i = 0
        if (arr != null) {
            while (i < arr.length()) {
                genre = if (i == 0) {
                    arr.getJSONObject(i).getString("name")
                } else {
                    "$genre, ${arr.getJSONObject(i).getString("name")}"
                }
                i++
            }
        }

        Glide.with(requireContext())
            .load("https://media.themoviedb.org/t/p/w220_and_h330_face/" + res.getString("poster_path"))
            .into(binding?.ivMoviePoster)
        binding?.tvMovieName?.text = res.getString("original_title")

        val formattedDate = DateUtils.formatDate(res.getString("release_date"))
        binding?.tvMovieReleaseDate?.text = formattedDate

        binding?.tvMovieOverview?.text = res.getString("overview")
        binding?.genres?.text = genre

        val voteAverage = res.getString("vote_average")?.toFloat()
        val gettingVoteAverage = (voteAverage?.times(100))
        binding?.tvUsersScore?.text = "${gettingVoteAverage.toString().substring(0, 2)}%"

        binding?.ivBackGround?.alpha = 0.2f

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/" + res.getString("backdrop_path"))
            .apply(RequestOptions().transform(CenterCrop()))
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(binding?.ivBackGround)

    }
}
