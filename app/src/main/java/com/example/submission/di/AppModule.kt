package com.example.submission.di

import com.example.submission.core.domain.usecase.MovieUseCase
import com.example.submission.detail.DetailViewModel
import com.example.submission.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { com.example.submission.core.domain.usecase.MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}