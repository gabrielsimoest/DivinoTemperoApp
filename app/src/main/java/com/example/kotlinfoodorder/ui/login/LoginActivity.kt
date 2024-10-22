package com.example.kotlinfoodorder.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.kotlinfoodorder.databinding.ActivityLoginBinding
import com.example.kotlinfoodorder.ui.register.RegisterActivity

class LoginActivity : ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFormObserver()

        initRegistryButtonListener()
    }

    private fun initFormObserver(){
        val userObserver = Observer<LoginUserModel> { user ->
            binding.emailEditText.setText(user.email)
            binding.passwordEditText.setText(user.password)
        }

        model.currentUser.observe(this, userObserver)
    }

    private fun initRegistryButtonListener(){
        binding.buttonRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}