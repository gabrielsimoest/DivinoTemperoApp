package com.example.kotlinfoodorder.auth.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.kotlinfoodorder.databinding.ActivityLoginBinding
import com.example.kotlinfoodorder.auth.ui.forgotpassword.ForgotPasswordActivity
import com.example.kotlinfoodorder.auth.ui.register.RegisterActivity
import com.example.kotlinfoodorder.menu.ui.menu.MenuActivity
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { action ->
                    executeAction(action)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onViewCreated()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentUser.collect { user ->
                    user?.let {
                        binding.emailEditText.error = if (it.email.isNotEmpty()) null else "Por favor, insira um email válido."
                        binding.passwordEditText.error = if (it.password.isNotEmpty()) null else "Por favor, insira uma senha válida."

                        binding.emailEditText.setText(it.email)
                        binding.passwordEditText.setText(it.password)
                    }
                }
            }
        }

        with(binding) {
            login.setOnClickListener {
                viewModel.onLoginClicked(getEmailText(), getPasswordText())
            }

            buttonRegister.setOnClickListener {
                navigateRegister()
            }

            forgotPasswordButton.setOnClickListener {
                navigateForgotPassword()
            }
        }
    }

    private fun executeAction(action: LoginAction) {
        when (action) {
            is LoginAction.NavigateMenu -> navigateMenu()
            is LoginAction.ShowErrorMessage -> showMessage(action.message ?: "Um erro aconteceu. Tente novamente.")
        }
    }

    private fun navigateMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateForgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun getEmailText() = binding.emailEditText.text.toString()
    private fun getPasswordText() = binding.passwordEditText.text.toString()

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
