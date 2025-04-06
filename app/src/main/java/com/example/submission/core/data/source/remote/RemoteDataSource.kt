package com.example.submission.core.data.source.remote

import com.example.submission.core.data.source.remote.network.ApiResponse
import com.example.submission.core.data.source.remote.network.ApiService
import com.example.submission.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllMovies(): Flow<ApiResponse<List<MovieResponse>>> = flow {
        try {
            val response = apiService.getPopularMovies()
            val movieList = response.results
            if (movieList.isNotEmpty()) {
                emit(ApiResponse.Success(movieList))
            } else {
                emit(ApiResponse.Empty("No data found"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
