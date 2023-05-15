package com.lacourt.githubusers.di

import com.lacourt.githubusers.viewmodel.MainViewModel
import com.lacourt.githubusers.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}