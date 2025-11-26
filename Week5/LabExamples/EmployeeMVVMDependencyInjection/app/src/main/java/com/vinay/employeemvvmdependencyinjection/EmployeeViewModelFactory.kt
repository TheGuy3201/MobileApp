package com.vinay.employeemvvmdependencyinjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//factory class to create view model
class EmployeeViewModelFactory(
    private val repository: EmployeeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
