package com.example.kotlinfoodorder.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<LoginUserModel?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun updateUser(user: LoginUserModel) {
        _currentUser.update {
            user
        }
    }
}
