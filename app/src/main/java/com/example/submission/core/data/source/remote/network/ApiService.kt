package com.example.submission.core.data.source.remote.network

import com.example.submission.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNWU4NTQxZTc2ZGZhMDRlZWI1OGM5YTM5OTA4MGJjMCIsIm5iZiI6MTc0MDMzMDYxMy4zMDA5OTk5LCJzdWIiOiI2N2JiNTY3NWQzNjMxNjk0NjY0Njc5N2MiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.tFUj7MHKvuLt_0z1Z0nLyuF2peDhsdogrk1tvuPD40U")
    @GET("movie/popular")
    suspend fun getPopularMovies(): ListMovieResponse
}