package com.example.kotlinfoodorder.menu.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel(
    private val loginRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val menuItemRepository: MenuItemRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = loginRepository.getCurrentUserInfo()
        }
    }

}