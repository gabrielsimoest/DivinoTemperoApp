package com.example.kotlinfoodorder.authManager.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.authManager.data.model.RegisterUserModel
import com.example.kotlinfoodorder.login.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val loginRepository: UserRepository
) : ViewModel() {
    private val _currentRegistry = MutableStateFlow<RegisterUserModel?>(null)
    val currentRegistry = _currentRegistry.asStateFlow()

    private val _uiAction = MutableSharedFlow<RegisterAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun onRegisterClicked(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch(Dispatchers.IO)
        {
            _currentRegistry.value = RegisterUserModel(
                name = name,
                email = email,
                password = password,
                confirmedPassword = confirmPassword
            )

            when {
                (password != confirmPassword) -> {
                    _uiAction.emit(RegisterAction.ShowErrorMessage("As senhas não coincidem."))
                }
                else -> {
                    runCatching {
                        loginRepository.createAccount(name, email, password)

                        _uiAction.emit(RegisterAction.ShowSuccessMessage)
                        _uiAction.emit(RegisterAction.NavigateLogin)
                    }.onFailure { e ->
                        _uiAction.emit(RegisterAction.ShowErrorMessage("Houve um erro ao realizar o login: ${e.message}"))
                    }
                }
            }
        }
    }
}
