package com.example.kotlinfoodorder.menu.model

data class OrderModel(
    val userId: Int,
    val items: MutableList<Int>
)
