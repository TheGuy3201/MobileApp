package com.example.apparchitectureexample.di

import com.example.apparchitectureexample.data.PetsRepository
import com.example.apparchitectureexample.data.PetsRepositoryImpl
import com.example.apparchitectureexample.viewmodel.PetsViewModel
import org.koin.dsl.module

val appModules = module {
    single<PetsRepository> { PetsRepositoryImpl() }
    single { PetsViewModel(get()) }
}