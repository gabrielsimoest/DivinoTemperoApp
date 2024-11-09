package com.example.kotlinfoodorder.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlinfoodorder.databinding.ActivityMenuBinding
import com.example.kotlinfoodorder.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MenuActivity : ComponentActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do ViewPager2 e do Adapter
        val viewPager: ViewPager2 = binding.viewPager
//        val adapter = MenuPagerAdapter(this)
//        viewPager.adapter = adapter

        // Configuração do TabLayout com o ViewPager
        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Comidas"
                1 -> tab.text = "Bebidas"
            }
        }.attach()

        // Configuração do botão de logout
        binding.logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
