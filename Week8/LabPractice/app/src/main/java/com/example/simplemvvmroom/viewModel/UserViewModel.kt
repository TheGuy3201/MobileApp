package com.example.simplemvvmroom.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemvvmroom.model.User
import com.example.simplemvvmroom.model.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val users: StateFlow<List<User>> = repository.allUsers
        .stateIn(
            scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = emptyList()
    )
    fun insertUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }
}