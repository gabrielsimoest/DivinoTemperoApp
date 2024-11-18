package com.example.kotlinfoodorder.menuManager.model

data class OrderModel(
    val userId: Int,
    val items: MutableList<Int>
)
