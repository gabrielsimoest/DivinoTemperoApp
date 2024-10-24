package com.example.kotlinfoodorder.ui.forgotpassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<ForgotUserModel?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun updateUser(user: ForgotUserModel) {
        _currentUser.update {
            user
        }
    }
}