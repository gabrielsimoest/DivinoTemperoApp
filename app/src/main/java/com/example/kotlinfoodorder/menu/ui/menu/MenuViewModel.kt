package com.example.kotlinfoodorder.menu.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.menu.data.repository.MenuCategoryRepository
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.menu.model.MenuCategoryModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val loginRepository: UserRepository,
    private val menuCategoryRepository: MenuCategoryRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _currentOrder = MutableStateFlow<Int>(0)
    val currentOrder = _currentOrder.asStateFlow()

    private val _currentCategories = MutableStateFlow<List<MenuCategoryModel>?>(null)
    val currentCategories = _currentCategories.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _uiAction = MutableSharedFlow<MenuAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = loginRepository.getCurrentUserInfo()
            _currentCategories.value = menuCategoryRepository.getMenuCategories()
            val user = _currentUser.value
            if (user != null)
                _currentOrder.value = orderRepository.getOrderNumberOfItems(user.uid)
        }
    }

    fun addItemToOrder(itemId: String) {
        val user = _currentUser.value
        if (user != null) {
            viewModelScope.launch {
                orderRepository.addItemToOrder(userId = user.uid, itemId = itemId)

                val orderNumberOfItems = orderRepository.getOrderNumberOfItems(user.uid)
                _currentOrder.value = orderNumberOfItems
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            loginRepository.logout();
            _uiAction.emit(MenuAction.NavigateHome)
        }
    }
}