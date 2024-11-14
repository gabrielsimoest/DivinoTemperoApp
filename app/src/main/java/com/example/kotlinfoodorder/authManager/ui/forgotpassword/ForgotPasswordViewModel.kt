package com.example.kotlinfoodorder.authManager.ui.forgotpassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.authManager.ui.login.LoginAction
import com.example.kotlinfoodorder.authManager.ui.login.LoginUserModel
import com.example.kotlinfoodorder.login.data.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _currentEmail = MutableStateFlow<String?>(null)
    val currentEmail = _currentEmail.asStateFlow()

    private val _uiAction = MutableSharedFlow<ForgotPasswordAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun onRecoverPasswordClicked(emailText: String) {
        _currentEmail.value = emailText

        viewModelScope.launch(Dispatchers.IO) {
            when {
                emailText.isEmpty() -> {
                    _uiAction.emit(ForgotPasswordAction.ShowErrorMessage("O campo de e-mail está vazio. Por favor, insira seu e-mail."))
                }
                else -> {
                    runCatching {
                        loginRepository.recover(emailText)
                        _uiAction.emit(ForgotPasswordAction.ShowEmailSendMessage)
                    }.onFailure { e ->
                        _uiAction.emit(ForgotPasswordAction.ShowErrorMessage(e.message))
                    }
                }
            }
        }
    }
}