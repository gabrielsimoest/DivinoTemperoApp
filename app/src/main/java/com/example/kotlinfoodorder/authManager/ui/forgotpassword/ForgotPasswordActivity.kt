package com.example.kotlinfoodorder.authManager.ui.forgotpassword

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinfoodorder.databinding.ActivityForgotPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.launch

class ForgotPasswordActivity : ComponentActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
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
                viewModel.currentEmail.collect { user ->
                    user?.let {
                        binding.emailEditText.error = if (it.isNotEmpty()) null else "Por favor, insira um email válido."
                    }
                }
            }
        }

        with(binding) {
            buttonGoLogin.setOnClickListener {
                finish()
            }

            sendCode.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                viewModel.onRecoverPasswordClicked(email)
            }
        }
    }

    private fun executeAction(action: ForgotPasswordAction) {
        when (action) {
            is ForgotPasswordAction.ShowEmailSendMessage -> showEmailSend()
            is ForgotPasswordAction.ShowErrorMessage -> showErrorMessage(action.message ?: "Um erro aconteceu. Tente novamente.")
        }
    }

    private fun showErrorMessage(message:String){
        binding.timerTextView.setText("Erro: ${message}")
        binding.timerTextView.visibility = View.VISIBLE
    }

    private fun showEmailSend(){
        Toast.makeText(
            this@ForgotPasswordActivity,
            "Código enviado!",
            Toast.LENGTH_SHORT
        ).show()

        binding.sendCode.text = "Reenviar Email"
        disableButtonFor30Seconds()
    }

    private fun disableButtonFor30Seconds() {
        binding.sendCode.isEnabled = false
        binding.timerTextView.visibility = View.VISIBLE

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = "Email enviado, Tente novamente em ${millisUntilFinished / 1000} segundos"
            }

            override fun onFinish() {
                binding.sendCode.isEnabled = true
                binding.timerTextView.visibility = View.GONE
            }
        }.start()
    }
}