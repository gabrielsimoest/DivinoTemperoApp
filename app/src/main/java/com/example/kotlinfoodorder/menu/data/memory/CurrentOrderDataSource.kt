package com.example.kotlinfoodorder.menu.data.memory

import kotlinx.coroutines.flow.StateFlow

interface CurrentOrderDataSource {
    val currentOrder : StateFlow<Int>
    fun updateOrderNumber()
}