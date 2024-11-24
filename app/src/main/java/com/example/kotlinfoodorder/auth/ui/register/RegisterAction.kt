package com.example.kotlinfoodorder.auth.ui.register

sealed class RegisterAction {
    data object NavigateLogin : RegisterAction()
    data object ShowSuccessMessage : RegisterAction()
    data class ShowErrorMessage(val message: String?) : RegisterAction()
}
