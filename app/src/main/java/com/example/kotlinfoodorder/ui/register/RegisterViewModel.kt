package com.example.kotlinfoodorder.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinfoodorder.data.model.RegisterUserModel
import com.example.kotlinfoodorder.ui.login.LoginUserModel

class RegisterViewModel : ViewModel() {
    val currentRegistry: MutableLiveData<RegisterUserModel> by lazy {
        MutableLiveData<RegisterUserModel>()
    }
}