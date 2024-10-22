package com.example.kotlinfoodorder.data.model

data class RegisterUserModel(
    val name: String,
    val email: String,
    val password: String,
    val confirmedPassword: String
)
