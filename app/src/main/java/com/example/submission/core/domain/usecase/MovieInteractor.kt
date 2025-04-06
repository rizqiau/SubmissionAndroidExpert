package com.example.submission.core.domain.usecase

import com.example.submission.core.data.Resource
import com.example.submission.core.domain.model.Movie
import com.example.submission.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovies()

    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)

    override fun isFavorite(movieId: Int): Flow<Boolean> = movieRepository.isFavorite(movieId)

    override fun getMovieDetail(movieId: Int): Flow<Movie> = movieRepository.getMovieDetail(movieId)
}