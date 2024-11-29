package com.example.kotlinfoodorder.order.model

data class OrderModel(
    var userId: String = "",
    var menuId: String = "",
    var quantity: Int = 0
)
