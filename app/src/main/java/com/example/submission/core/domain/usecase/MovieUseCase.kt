package com.example.submission.core.domain.usecase

import com.example.submission.core.data.Resource
import com.example.submission.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
    fun isFavorite(movieId: Int): Flow<Boolean>
    fun getMovieDetail(movieId: Int): Flow<Movie>
}