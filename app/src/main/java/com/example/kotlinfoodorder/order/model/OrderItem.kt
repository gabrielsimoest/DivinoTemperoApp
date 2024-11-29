package com.example.kotlinfoodorder.order.model

data class OrderItem(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var imagePath: String = "",
    var quantity: Int = 0
)
