package com.example.kotlinfoodorder.menu.model

data class OrderItem(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var imageResId: Int = 0,
    var quantity: Int = 0
)
