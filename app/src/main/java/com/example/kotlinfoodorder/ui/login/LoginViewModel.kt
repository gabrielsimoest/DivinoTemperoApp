package com.example.kotlinfoodorder.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel()  {
    val currentUser: MutableLiveData<LoginUserModel> by lazy {
        MutableLiveData<LoginUserModel>()
    }

}