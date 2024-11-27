package com.example.kotlinfoodorder.menu.ui.menu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodorder.databinding.ActivityCartBinding
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import com.example.kotlinfoodorder.menu.ui.order.OrderAdapter
import com.example.kotlinfoodorder.menu.ui.order.OrderViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderActivity : ComponentActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel: OrderViewModel by viewModel()

    private val menuItemRepository: MenuItemRepository by inject()
    private val orderRepository: OrderRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            binding.recyclerViewMenu.layoutManager = GridLayoutManager(this@OrderActivity, 1)

            val user = viewModel.currentUser.value
            if (user != null) {
                val adapter = OrderAdapter(
                    menuItemRepository,
                    orderRepository,
                    user.uid,
                    orderTotal
                )

                recyclerViewMenu.adapter = adapter

                lifecycleScope.launch {
                    adapter.loadItems()
                }
            }

            btnBack.setOnClickListener { finish() }
        }
    }
}