package com.vinay.employeemvvmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vinay.employeemvvmapp.ui.theme.EmployeeMVVMAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmployeeMVVMAppTheme {
                EmployeeScreen(viewModel = viewModel())
            }
        }
    }
}

@Composable
fun EmployeeScreen(viewModel: EmployeeViewModel) {
    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }

    val employeeList by viewModel.employees.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("Employee ID") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Employee Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = salary,
            onValueChange = { salary = it },
            label = { Text("Salary") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                viewModel.addEmployee(
                    id.toIntOrNull() ?: 0,
                    name,
                    salary.toDoubleOrNull() ?: 0.0
                )
                id = ""
                name = ""
                salary = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Employee")
        }


        Text("EmployeeList:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(employeeList.size) { index ->
                val employee = employeeList[index]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // Corrected modifier
                ) { // This is the start of the Column's content lambda
                    Text("ID: ${employee.id}")
                    Text("Name: ${employee.name}")
                    Text("Salary: ${employee.salary}")
                    Divider()
                }
            }
        }

    }
}