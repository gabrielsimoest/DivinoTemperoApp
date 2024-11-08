package com.example.kotlinfoodorder.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlinfoodorder.databinding.ActivityLoginBinding
import com.example.kotlinfoodorder.databinding.ActivityMenuBinding
import com.example.kotlinfoodorder.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : ComponentActivity() {
    private lateinit var binding: ActivityMenuBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}