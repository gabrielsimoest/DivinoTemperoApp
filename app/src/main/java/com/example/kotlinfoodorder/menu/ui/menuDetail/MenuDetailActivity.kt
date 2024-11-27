package com.example.kotlinfoodorder.menu.ui.menuDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinfoodorder.databinding.ActivityMenuDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.Locale

class MenuDetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityMenuDetailBinding
    private val viewModel: MenuDetailViewModel by viewModel()

    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuItemId = intent.getStringExtra("menuItemId") ?: "-1"
        if (menuItemId != "-1") {
            viewModel.loadMenuItem(menuItemId)

            binding.btnAddItem.setOnClickListener{ viewModel.addItemToOrder(menuItemId) }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentMenuItem.collect { item ->
                    item?.let {
                        binding.itemImage.setImageResource(item.imageResId)
                        binding.itemName.text = item.name
                        binding.itemDescription.text = item.description
                        binding.itemPrice.text = currencyFormat.format(item.price)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentOrder.collect { order ->
                    order.toString().also { binding.order.text = it }
                }
            }
        }

        binding.btnBack.setOnClickListener { finish() }
    }
}