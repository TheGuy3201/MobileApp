package com.vinay.employeemvvmdependencyinjection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {

    val employees: LiveData<List<Employee>> = repository.getEmployees()

    fun addEmployee(id: Int, name: String, salary: Double) {
        val newEmployee = Employee(id, name, salary)
        repository.addEmployee(newEmployee)
    }
}