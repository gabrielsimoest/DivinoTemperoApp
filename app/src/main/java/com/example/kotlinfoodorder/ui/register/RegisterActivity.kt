package com.example.kotlinfoodorder.ui.register

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
            binding.editTextName.setText(registry.name)
            binding.editTextEmail.setText(registry.email)
            binding.editTextPassword.setText(registry.password)
            binding.editTextConfirmPassword.setText(registry.confirmedPassword)
        }

        model.currentRegistry.observe(this, registryObserver)
    }
}