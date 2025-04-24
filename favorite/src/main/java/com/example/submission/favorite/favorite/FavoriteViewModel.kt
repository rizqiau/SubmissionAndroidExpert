package com.example.submission.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovies = movieUseCase.getFavoriteMovies().asLiveData()
}
