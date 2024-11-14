package com.example.kotlinfoodorder.authManager.ui.register

sealed class RegisterAction {
    data object NavigateLogin : RegisterAction()
    data object ShowSuccessMessage : RegisterAction()
    data class ShowErrorMessage(val message: String?) : RegisterAction()
}
