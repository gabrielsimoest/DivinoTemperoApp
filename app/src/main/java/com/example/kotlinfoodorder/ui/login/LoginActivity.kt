package com.example.kotlinfoodorder.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinfoodorder.databinding.ActivityLoginBinding
import com.example.kotlinfoodorder.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFormObserver()
        initButtonListeners()
    }

    private fun initFormObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentUser.collect { user ->
                    user?.let {
                        binding.emailEditText.error = if (!it.email.isNullOrEmpty()) null else "Por favor, insira um email válido."
                        binding.passwordEditText.error = if (!it.password.isNullOrEmpty()) null else "Por favor, insira uma senha válida."

                        binding.emailEditText.setText(it.email)
                        binding.passwordEditText.setText(it.password)
                    }
                }
            }
        }
    }

    private fun initButtonListeners() {
        initLoginButtonListener()
        initRegistryButtonListener()
    }

    private fun initLoginButtonListener() {
        binding.login.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
//                viewModel.login(email, password)
            } else {
                if (email.isEmpty()) {
                    binding.emailEditText.error = "Por favor, insira um email."
                }
                if (password.isEmpty()) {
                    binding.passwordEditText.error = "Por favor, insira uma senha."
                }
            }
        }
    }

    private fun initRegistryButtonListener() {
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
