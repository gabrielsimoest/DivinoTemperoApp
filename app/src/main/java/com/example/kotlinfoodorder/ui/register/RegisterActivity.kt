package com.example.kotlinfoodorder.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.kotlinfoodorder.data.model.RegisterUserModel
import com.example.kotlinfoodorder.databinding.ActivityRegisterBinding

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val model: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registryObserver = Observer<RegisterUserModel> { registry ->
            binding.nomeEditText.setText(registry.name)
            binding.emailEditText.setText(registry.email)
            binding.passwordEditText.setText(registry.password)
            binding.confirmPasswordEditText.setText(registry.confirmedPassword)
        }

        model.currentRegistry.observe(this, registryObserver)
        initLoginButtonListener();
    }

    private fun initLoginButtonListener() {
        binding.buttonGoLogin.setOnClickListener {
            finish()
        }
    }
}