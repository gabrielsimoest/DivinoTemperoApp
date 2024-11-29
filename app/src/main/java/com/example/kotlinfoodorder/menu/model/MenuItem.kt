package com.example.kotlinfoodorder.menu.model

data class MenuItem(
    var id: String = "",
    var categoryId: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var imagePath: String = ""
)