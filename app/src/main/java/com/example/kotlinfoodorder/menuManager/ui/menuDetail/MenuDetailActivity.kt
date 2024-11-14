package com.example.kotlinfoodorder.menuManager.ui.menuDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinfoodorder.authManager.ui.login.LoginViewModel
import com.example.kotlinfoodorder.databinding.ActivityMenuDetailBinding
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class MenuDetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityMenuDetailBinding
    private val viewModel: MenuDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuItemId = intent.getIntExtra("menuItemId", -1)
        if (menuItemId != -1) {
            viewModel.loadMenuItem(menuItemId)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentMenuItem.collect { item ->
                    item?.let {
                        binding.itemImage.setImageResource(item.imageResId)
                        binding.itemName.text = item.name
                        binding.itemDescription.text = item.description
                        binding.itemPrice.setText(item.price.toString())
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}