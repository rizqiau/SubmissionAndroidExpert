package com.example.submission.core.data

import com.example.submission.core.data.source.local.LocalDataSource
import com.example.submission.core.data.source.remote.RemoteDataSource
import com.example.submission.core.data.source.remote.network.ApiResponse
import com.example.submission.core.data.source.remote.response.MovieResponse
import com.example.submission.core.domain.model.Movie
import com.example.submission.core.domain.repository.IMovieRepository
import com.example.submission.core.utils.AppExecutors
import com.example.submission.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> =
        localDataSource.getMovieDetail(movieId).map { it.isFavorite }

    override fun getMovieDetail(movieId: Int): Flow<Movie> =
        localDataSource.getMovieDetail(movieId).map {
            DataMapper.mapEntityToDomain(it)
        }
}