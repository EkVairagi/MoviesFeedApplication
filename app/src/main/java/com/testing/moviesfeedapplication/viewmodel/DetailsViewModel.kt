package com.testing.moviesfeedapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testing.moviesfeedapplication.repository.DetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class DetailsViewModel : ViewModel() {
    private val repository = DetailsRepository()
    private val _movieDetails = MutableStateFlow<Result<JSONObject>?>(null)
    val movieDetails: StateFlow<Result<JSONObject>?> get() = _movieDetails

    fun fetchMovieDetails(id: String) {
        viewModelScope.launch {
            repository.getMovieDetails(id) { result ->
                _movieDetails.value = result
            }
        }
    }
}

