package com.example.submission.core.data.source.local

import com.example.submission.core.data.source.local.entity.MovieEntity
import com.example.submission.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao)
            }
    }

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun getMovieDetail(movieId: Int): Flow<MovieEntity> = movieDao.getMovieDetail(movieId)

    suspend fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovies(movieList)

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movieDao.updateFavoriteMovie(movie.copy(isFavorite = newState))
    }
}