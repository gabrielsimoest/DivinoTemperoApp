package com.example.kotlinfoodorder.menu.ui.menuDetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.kotlinfoodorder.R
import com.example.kotlinfoodorder.databinding.ActivityMenuDetailBinding
import com.example.kotlinfoodorder.menu.ui.menu.OrderActivity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
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

            showLoader(true)
            try {
                viewModel.loadMenuItem(menuItemId)
            } finally {
                showLoader(false)
            }

            binding.btnAddItem.setOnClickListener{ viewModel.addItemToOrder(menuItemId) }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentMenuItem.collect { item ->
                    item?.let {
                        val storageReference = FirebaseStorage.getInstance().reference.child(item.imagePath)

                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            Glide.with(binding.itemImage.context)
                                .load(uri)
                                .into(binding.itemImage)
                        }.addOnFailureListener {
                            binding.itemImage.setImageResource(R.drawable.ic_food_placeholder)
                        }

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
        binding.order.setOnClickListener { navigateToCart() }

    }

    private fun navigateToCart() {
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
    }

    private fun showLoader(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

}