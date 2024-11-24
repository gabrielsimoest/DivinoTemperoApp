package com.example.kotlinfoodorder.menu.ui.menu

data class MenuItem(
    var id: String = "",
    var categoryId: String = "",
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var imageResId: Int = 0
)