package com.example.kotlinfoodorder.auth.ui.forgotpassword

sealed class ForgotPasswordAction {
    data object ShowEmailSendMessage : ForgotPasswordAction()
    data class ShowErrorMessage(val message: String?) : ForgotPasswordAction()
}
