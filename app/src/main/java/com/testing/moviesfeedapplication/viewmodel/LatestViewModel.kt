package com.testing.moviesfeedapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testing.moviesfeedapplication.model.Result
import com.testing.moviesfeedapplication.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LatestViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _movies = MutableStateFlow<List<Result>?>(null)
    val movies: StateFlow<List<Result>?> get() = _movies
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchTrendingMovies() {
        viewModelScope.launch {
            repository.getTrendingMovies { results, errorMessage ->
                if (results != null) {
                    _movies.value = results
                } else {
                    _error.value = errorMessage
                }
            }
        }
    }
}