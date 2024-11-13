package com.example.kotlinfoodorder.authManager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.login.data.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<LoginUserModel?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState = _loginState.asSharedFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        checkIfUserIsAuthenticated()
    }

    private fun checkIfUserIsAuthenticated() {
        val user = auth.currentUser
        if (user != null) {
            _currentUser.value = LoginUserModel(user.email ?: "", "")
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Idle
        }
    }

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _currentUser.value = LoginUserModel(email, password)
                            _loginState.value = LoginState.Success
                        } else {
                            _loginState.value = LoginState.Error(task.exception?.message)
                        }
                    }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message)
            }
        }
    }

    fun updateUser(user: LoginUserModel) {
        _currentUser.value = user
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String?) : LoginState()
    }
}
