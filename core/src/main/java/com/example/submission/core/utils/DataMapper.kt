package com.example.submission.core.utils

import com.example.submission.core.data.source.local.entity.MovieEntity
import com.example.submission.core.data.source.remote.response.MovieResponse
import com.example.submission.core.domain.model.Movie

object DataMapper {

    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        return input.map {
            MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = false // default karena dari API
            )
        }
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        return input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }
    }

    fun mapEntityToDomain(entity: MovieEntity): Movie =
        Movie(
            id = entity.id,
            title = entity.title,
            overview = entity.overview,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            voteAverage = entity.voteAverage,
            isFavorite = entity.isFavorite
        )

    fun mapDomainToEntity(domain: Movie): MovieEntity =
        MovieEntity(
            id = domain.id,
            title = domain.title,
            overview = domain.overview,
            posterPath = domain.posterPath,
            releaseDate = domain.releaseDate,
            voteAverage = domain.voteAverage,
            isFavorite = false // default, override di repository
        )
}