package com.example.kotlinfoodorder.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<LoginUserModel?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

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
