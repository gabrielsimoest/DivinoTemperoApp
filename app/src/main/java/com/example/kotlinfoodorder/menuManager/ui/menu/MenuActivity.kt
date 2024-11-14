package com.example.kotlinfoodorder.menuManager.ui.menu

import android.R.color
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodorder.databinding.ActivityMenuBinding
import com.example.kotlinfoodorder.authManager.ui.login.LoginActivity
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menuManager.ui.menuDetail.MenuDetailActivity
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

class MenuActivity : ComponentActivity() {
    private lateinit var binding: ActivityMenuBinding

    private val viewModel: MenuViewModel by viewModel()
    private val menuItemRepository: MenuItemRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
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
                viewModel.currentUser.collect { user ->
                    user?.let {
                        binding.infoTextPrimary.text = "Olá, ${user.displayName}"
                    }
                }
            }
        }

        addFilterButtons()

        val adapter = MenuAdapter(menuItemRepository) { item ->
            navigateItemDetail(item)
        }

        with(binding) {
            recyclerViewMenu.layoutManager = GridLayoutManager(this@MenuActivity, 2)
            recyclerViewMenu.adapter = adapter

            lifecycleScope.launch {
                adapter.loadItems()
            }

            logout.setOnClickListener {
                viewModel.logout()
            }

            cart.setOnClickListener {
                navigateToCart()
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.currentCategories.collect { category ->
                        category?.forEach { label ->
                            val button = MaterialButton(this@MenuActivity).apply {
                                text = label.name
                                layoutParams = LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    setMargins(16, 0, 16, 0)
                                }
                                setTextColor(ContextCompat.getColor(this@MenuActivity, color.white))
                            }
                            binding.filterButtonsLayoutContainer.addView(button)
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onViewCreated()
            }
        }
    }

    private fun executeAction(action: MenuAction) {
        when (action) {
            is MenuAction.NavigateHome -> navigateHome()
            is MenuAction.ShowErrorMessage -> showMessage(action.message ?: "Um erro aconteceu. Tente novamente.")
        }
    }

    private fun navigateHome() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToCart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    private fun navigateItemDetail(item: MenuItem) {
        val intent = Intent(this, MenuDetailActivity::class.java)
        intent.putExtra("itemName", item.name)
        startActivity(intent)
    }

    private fun addFilterButtons() {
        val buttonLabels = listOf("Todos", "Comidas", "Bebidas", "Sobremesas", "Outros")

        buttonLabels
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
