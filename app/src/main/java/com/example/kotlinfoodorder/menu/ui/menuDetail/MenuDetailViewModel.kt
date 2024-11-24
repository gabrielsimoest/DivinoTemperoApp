package com.example.kotlinfoodorder.menu.ui.menuDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import com.example.kotlinfoodorder.menu.ui.menu.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuDetailViewModel(
    private val loginRepository: UserRepository,
    private val menuItemRepository: MenuItemRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _currentMenuItem = MutableStateFlow<MenuItem?>(null)
    val currentMenuItem = _currentMenuItem.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _currentOrder = MutableStateFlow<Int>(0)
    val currentOrder = _currentOrder.asStateFlow()

    init {
        viewModelScope.launch {
            val user = loginRepository.getCurrentUserInfo()

            _currentUser.value = user
            val orderNumberOfItems = orderRepository.getOrderNumberOfItems(user.uid)
            _currentOrder.value = orderNumberOfItems
        }
    }

    fun loadMenuItem(id: String) {
        viewModelScope.launch {
            _currentMenuItem.value = menuItemRepository.getMenuItem(id)
        }
    }

    fun addItemToOrder(itemId: String) {
        val user = _currentUser.value
        if (user != null) {
            orderRepository.addItemToOrder(userId = user.uid, itemId = itemId)

            val orderNumberOfItems = orderRepository.getOrderNumberOfItems(user.uid)
            _currentOrder.value = orderNumberOfItems
        }
    }
}