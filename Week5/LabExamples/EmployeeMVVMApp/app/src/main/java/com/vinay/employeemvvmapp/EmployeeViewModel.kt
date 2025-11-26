package com.vinay.employeemvvmapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeViewModel: ViewModel() {

    private val _employees = MutableLiveData<List<Employee>>(emptyList())
    val employees: LiveData<List<Employee>> = _employees

    fun addEmployee(id: Int, name: String, salary: Double) {
        val newEmployee = Employee(id, name, salary)
        _employees.value = _employees.value?.plus(newEmployee)
    }
}