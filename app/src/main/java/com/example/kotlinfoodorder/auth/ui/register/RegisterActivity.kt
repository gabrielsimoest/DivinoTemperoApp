package com.example.kotlinfoodorder.auth.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.kotlinfoodorder.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
                viewModel.currentRegistry.collect { user ->
                    user?.let {
                        binding.nomeEditText.error = if (it.name.isNotEmpty()) null else "Por favor, insira um nome válido."
                        binding.emailEditText.error = if (it.email.isNotEmpty()) null else "Por favor, insira um email válido."
                        binding.passwordEditText.error = if (it.password.isNotEmpty()) null else "Por favor, insira uma senha válida."
                        binding.confirmPasswordEditText.error = if (it.confirmedPassword.isNotEmpty()) null else "Por favor, insira uma senha válida."

                        binding.nomeEditText.setText(it.name)
                        binding.emailEditText.setText(it.email)
                        binding.passwordEditText.setText(it.password)
                        binding.confirmPasswordEditText.setText(it.confirmedPassword)
                    }
                }
            }
        }

        with(binding) {
            createAccount.setOnClickListener {
                viewModel.onRegisterClicked(getNameText(), getEmailText(), getPasswordText(), getConfirmedPasswordText())
            }

            buttonGoLogin.setOnClickListener {
                navigateLogin()
            }
        }
    }

    private fun executeAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.NavigateLogin -> navigateLogin()
            is RegisterAction.ShowSuccessMessage -> showMessage("Usuário criado com sucesso!")
            is RegisterAction.ShowErrorMessage -> showMessage(action.message ?: "Um erro aconteceu. Tente novamente.")
        }
    }

    private fun getNameText() = binding.nomeEditText.text.toString()
    private fun getEmailText() = binding.emailEditText.text.toString()
    private fun getPasswordText() = binding.passwordEditText.text.toString()
    private fun getConfirmedPasswordText() = binding.confirmPasswordEditText.text.toString()

    private fun navigateLogin(){
        finish()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}