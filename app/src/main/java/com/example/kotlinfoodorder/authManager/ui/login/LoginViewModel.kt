package com.example.kotlinfoodorder.authManager.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.login.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: UserRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<LoginUserModel?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _uiAction = MutableSharedFlow<LoginAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun onLoginClicked(email: String, password: String) {
        _currentUser.value = LoginUserModel(email, password)

        viewModelScope.launch(Dispatchers.IO) {
            when {
                email.isEmpty() -> {
                    _uiAction.emit(LoginAction.ShowErrorMessage("O campo de e-mail está vazio. Por favor, insira seu e-mail."))
                }
                password.isEmpty() -> {
                    _uiAction.emit(LoginAction.ShowErrorMessage("O campo de senha está vazio. Por favor, insira sua senha."))
                }
                else -> {
                    runCatching {
                        loginRepository.login(email, password)
                        _uiAction.emit(LoginAction.NavigateMenu)
                    }.onFailure { e ->
                        _uiAction.emit(LoginAction.ShowErrorMessage("Houve um erro ao realizar o login: ${e.message}"))
                    }
                }
            }
        }
    }

    fun onViewCreated() {
        viewModelScope.launch {
            if (loginRepository.isSessionValid()) {
                _uiAction.emit(LoginAction.NavigateMenu)
            }
        }
    }
}
