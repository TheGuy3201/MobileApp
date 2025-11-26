package com.example.simplemvvmroom.view
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.simplemvvmroom.ui.theme.SimpleMVVMRoomTheme
import com.example.simplemvvmroom.model.User
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.simplemvvmroom.model.UserDatabase
import com.example.simplemvvmroom.model.UserRepository
import com.example.simplemvvmroom.viewModel.UserViewModel
import com.example.simplemvvmroom.viewModel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(UserRepository(UserDatabase.getDatabase(application).userDao()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleMVVMRoomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserListScreen(userViewModel)
                }
            }
        }
    }
}
@Composable
fun UserListScreen(userViewModel: UserViewModel) {
    val users by userViewModel.users.collectAsState()
    val (name, setName) = remember { mutableStateOf("") }
    val (age, setAge) = remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = setName,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = age,
            onValueChange = setAge,
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val user = User(name = name, age = age.toIntOrNull() ?: 0)
            userViewModel.insertUser(user)
            setName("")
            setAge("")
        }) {
            Text("Add User")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}
@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Name: ${user.name}", style =
            MaterialTheme.typography.bodyLarge)
        Text(text = "Age: ${user.age}", style =
            MaterialTheme.typography.bodyMedium)
    }
}