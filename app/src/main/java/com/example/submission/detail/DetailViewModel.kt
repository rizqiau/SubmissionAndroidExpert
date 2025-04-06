package com.example.submission.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission.core.domain.model.Movie
import com.example.submission.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> get() = _selectedMovie

    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun toggleFavorite() {
        _selectedMovie.value?.let { currentMovie ->
            val newState = !currentMovie.isFavorite
            val updatedMovie = currentMovie.copy(isFavorite = newState)
            _selectedMovie.value = updatedMovie

            viewModelScope.launch {
                movieUseCase.setFavoriteMovie(updatedMovie, newState)
            }
        }
    }
}

