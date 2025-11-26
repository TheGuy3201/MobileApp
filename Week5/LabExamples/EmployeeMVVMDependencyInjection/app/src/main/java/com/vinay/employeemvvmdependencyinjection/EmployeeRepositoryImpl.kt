package com.vinay.employeemvvmdependencyinjection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//implementation of repository using EmployeeRepositoryImpl class
class EmployeeRepositoryImpl : EmployeeRepository {
    //live data to hold the list of employees
    private val employeeList = MutableLiveData<List<Employee>>(emptyList())
    //definition of getEmployees method
    override fun getEmployees(): LiveData<List<Employee>> = employeeList
    //definition of addEmployee method
    override fun addEmployee(employee: Employee) {
        employeeList.value = employeeList.value?.plus(employee)
    }
}