package com.example.kotlinfoodorder.order.data.memory

import kotlinx.coroutines.flow.StateFlow

interface CurrentOrderDataSource {
    val currentOrder : StateFlow<Int>
    fun updateOrderNumber()
}