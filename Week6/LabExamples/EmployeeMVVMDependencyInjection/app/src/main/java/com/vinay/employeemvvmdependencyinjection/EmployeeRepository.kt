package com.vinay.employeemvvmdependencyinjection

import androidx.lifecycle.LiveData

//interface for repository to implement dependency injection
interface EmployeeRepository {
    //signature of methods to be implemented
    fun getEmployees(): LiveData<List<Employee>>
    fun addEmployee(employee: Employee)
}