package com.example.kotlinfoodorder.authManager.ui.login

sealed class LoginAction {
    data object NavigateMenu : LoginAction()
    data class ShowErrorMessage(val message: String?) : LoginAction()
}
