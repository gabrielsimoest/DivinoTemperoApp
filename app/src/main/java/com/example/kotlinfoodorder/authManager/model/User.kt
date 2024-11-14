package com.example.kotlinfoodorder.login.model

import android.net.Uri

data class User(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: Uri?,
    val phoneNumber: String?,
    val isEmailVerified: Boolean
)
