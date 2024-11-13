package com.example.kotlinfoodorder.authManager.ui.forgotpassword

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinfoodorder.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.launch

class ForgotPasswordActivity : ComponentActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFormObserver()
        initLoginButtonListener()
        initSendCodeButtonListener()
    }

    private fun initFormObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.forgotState.collect { state ->
                    when (state) {
                        is ForgotPasswordViewModel.ForgotState.Loading -> {
                        }

                        is ForgotPasswordViewModel.ForgotState.Send -> {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Código enviado!",
                                Toast.LENGTH_SHORT
                            ).show()

                            binding.sendCode.text = "Reenviar Email"
                            disableButtonFor30Seconds()
                        }

                        is ForgotPasswordViewModel.ForgotState.Error -> {
                            binding.timerTextView.text = "Erro: ${state.message}"
                            binding.timerTextView.visibility = View.VISIBLE
                        }

                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun initSendCodeButtonListener() {
        binding.sendCode.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            if (email.isNotEmpty()) {
                viewModel.sendCode(email)
            } else {
                binding.emailEditText.error = "Por favor, insira um email."
            }
        }
    }

    private fun initLoginButtonListener() {
        binding.buttonGoLogin.setOnClickListener {
            finish()
        }
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