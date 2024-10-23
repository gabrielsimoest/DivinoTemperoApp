package com.example.kotlinfoodorder.data.model

import java.util.Date

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val dateOfRegister: Date
)
