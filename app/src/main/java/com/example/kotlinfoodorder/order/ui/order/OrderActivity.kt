package com.example.kotlinfoodorder.menu.ui.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodorder.databinding.ActivityCartBinding
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.order.data.repository.OrderRepository
import com.example.kotlinfoodorder.order.ui.order.OrderAdapter
import com.example.kotlinfoodorder.order.ui.order.OrderViewModel
import kotlinx.coroutines.delay
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
            recyclerViewMenu.layoutManager = GridLayoutManager(this@OrderActivity, 1)

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
                    showLoader(true)
                    try {
                        adapter.loadItems()
                    } finally {
                        showLoader(false)
                    }
                }


                lifecycleScope.launch {
                    viewModel.orderSuccess.collect { orderSuccess ->
                        if (orderSuccess) {
                            showLoader(true)
                            try {
                                adapter.loadItems()
                            } finally {
                                showLoader(false)
                            }
                            showToast("Pedido realizado com sucesso!")
                        } else {
                            showToast("Erro ao realizar o pedido. Tente novamente.")
                        }
                    }
                }
            }

            btnBack.setOnClickListener { finish() }
            btnCheckout.setOnClickListener { viewModel.makeOrder() }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoader(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
