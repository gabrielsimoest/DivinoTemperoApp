package com.example.kotlinfoodorder.ui.menuDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.kotlinfoodorder.R
import com.example.kotlinfoodorder.databinding.ActivityMenuDetailBinding
import com.example.kotlinfoodorder.ui.menu.Item
import kotlin.random.Random

class MenuDetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityMenuDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = generateRandomItem()

        binding.btnBack.setOnClickListener { finish() }
//        binding.btnAddToCart.setOnClickListener {
//        }

        binding.itemImage.setImageResource(item.imageResId)
        binding.itemName.text = item.name
        binding.itemDescription.text = item.description
        binding.itemPrice.setText(item.price.toString())
    }

    private fun generateRandomItem(): Item {
        val names = listOf("Pizza", "Burger", "Pasta", "Salad", "Sushi")
        val descriptions = listOf(
            "Delicious and freshly made.",
            "Perfect for any meal.",
            "A popular choice among food lovers.",
            "Healthy and tasty.",
            "Authentic and flavorful."
        )
        val randomName = names[Random.nextInt(names.size)]
        val randomDescription = descriptions[Random.nextInt(descriptions.size)]
        val randomPrice = Random.nextDouble(5.0, 25.0)
        val randomImageResId = android.R.drawable.ic_menu_report_image

        return Item(
            name = randomName,
            description = randomDescription,
            price = randomPrice,
            imageResId = randomImageResId
        )
    }
}