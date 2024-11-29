package com.example.kotlinfoodorder.order.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.order.data.repository.OrderRepository
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.menu.ui.menu.MenuAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val loginRepository: UserRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _orderSuccess = MutableSharedFlow<Boolean>(replay = 0)
    val orderSuccess = _orderSuccess.asSharedFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = loginRepository.getCurrentUserInfo()
        }
    }

    fun makeOrder() {
        viewModelScope.launch {
            val user = _currentUser.value
            if (user != null) {
                try {
                    orderRepository.placeOrder(user.uid)
                    _orderSuccess.emit(true)
                } catch (e: Exception) {
                    _orderSuccess.emit(false)
                }
            }
        }
    }

}