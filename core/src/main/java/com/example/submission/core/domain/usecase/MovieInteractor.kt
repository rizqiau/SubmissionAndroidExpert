package com.example.submission.core.domain.usecase

import com.example.submission.core.domain.model.Movie
import com.example.submission.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) :
    com.example.submission.core.domain.usecase.MovieUseCase {
    override fun getAllMovies() = movieRepository.getAllMovies()

    override fun getFavoriteMovies() = movieRepository.getFavoriteMovies()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        movieRepository.setFavoriteMovie(movie, state)
    }
}