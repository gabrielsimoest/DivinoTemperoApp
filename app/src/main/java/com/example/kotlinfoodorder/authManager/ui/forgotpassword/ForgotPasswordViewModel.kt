package com.example.kotlinfoodorder.authManager.ui.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.authManager.ui.login.LoginUserModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val _forgotState = MutableStateFlow<ForgotState>(ForgotState.Idle)
    val forgotState = _forgotState.asStateFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun sendCode(email: String) {
        _forgotState.value = ForgotState.Loading
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _forgotState.value = ForgotState.Send
                        } else {
                            _forgotState.value = ForgotState.Error(task.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                _forgotState.value = ForgotState.Error(e.message)
            }
        }
    }

    sealed class ForgotState {
        object Idle : ForgotState()
        object Loading : ForgotState()
        object Send : ForgotState()
        data class Error(val message: String?) : ForgotState()
    }
}