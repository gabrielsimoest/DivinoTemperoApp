package com.example.kotlinfoodorder.authManager.ui.forgotpassword

sealed class ForgotPasswordAction {
    data object ShowEmailSendMessage : ForgotPasswordAction()
    data class ShowErrorMessage(val message: String?) : ForgotPasswordAction()
}
