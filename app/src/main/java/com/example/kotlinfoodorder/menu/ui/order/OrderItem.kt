package com.example.kotlinfoodorder.menu.ui.menu

data class OrderItem(
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    var quantity: Int = 1
)
