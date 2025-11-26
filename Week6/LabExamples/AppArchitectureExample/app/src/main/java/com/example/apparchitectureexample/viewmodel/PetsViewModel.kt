package com.example.apparchitectureexample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.apparchitectureexample.data.PetsRepository

class PetsViewModel(
    private val petsRepository: PetsRepository
): ViewModel() {

    fun getPets() = petsRepository.getPets()
}