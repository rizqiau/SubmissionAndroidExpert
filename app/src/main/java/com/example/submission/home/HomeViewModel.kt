package com.example.submission.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission.core.data.Resource
import com.example.submission.core.domain.model.Movie
import com.example.submission.core.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    val movies: LiveData<Resource<List<Movie>>> = movieUseCase.getAllMovies().asLiveData()
}