package com.example.kotlinfoodorder.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinfoodorder.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLoginButtonListener();
        initObserver();
        initRegisterButtonListener();
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentRegistry.collect { user ->
                    user?.let {
                        binding.nomeEditText.error = if (!it.email.isNullOrEmpty()) null else "Por favor, insira um nome válido."
                        binding.emailEditText.error = if (!it.email.isNullOrEmpty()) null else "Por favor, insira um email válido."
                        binding.passwordEditText.error = if (!it.password.isNullOrEmpty()) null else "Por favor, insira uma senha válida."
                        binding.passwordEditText.error = if (!it.password.isNullOrEmpty()) null else "Por favor, insira uma senha válida."

                        binding.emailEditText.setText(it.name)
                        binding.passwordEditText.setText(it.email)
                        binding.passwordEditText.setText(it.password)
                        binding.passwordEditText.setText(it.confirmedPassword)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registryState.collect { state ->
                    when (state) {
                        is RegisterViewModel.RegistryState.Loading -> {
                        }

                        is RegisterViewModel.RegistryState.Success -> {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Usuario criado com sucesso!",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        }

                        is RegisterViewModel.RegistryState.Error -> {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Erro: ${state.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Sem usuario encontrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun initRegisterButtonListener() {
        binding.createAccount.setOnClickListener {
            val name = binding.nomeEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                viewModel.register(name, email, password, confirmPassword)
            } else {
                if (name.isEmpty()) {
                    binding.nomeEditText.error = "Por favor, insira um nome."
                }
                if (email.isEmpty()) {
                    binding.emailEditText.error = "Por favor, insira um email."
                }
                if (password.isEmpty()) {
                    binding.passwordEditText.error = "Por favor, insira uma senha."
                }
                if (confirmPassword.isEmpty()) {
                    binding.confirmPasswordEditText.error = "Por favor, insira uma senha."
                }
            }
        }
    }

    private fun initLoginButtonListener() {
        binding.buttonGoLogin.setOnClickListener {
            finish()
        }
    }
}