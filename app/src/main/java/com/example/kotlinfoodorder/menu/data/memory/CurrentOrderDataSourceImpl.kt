package com.example.kotlinfoodorder.menu.data.memory

import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.menu.data.remote.MenuItemRemoteDatasource
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentOrderDataSourceImpl(
    private val loginRepository: UserRepository,
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource,
) : CurrentOrderDataSource {
    private val _currentOrder = MutableStateFlow(0)
    override val currentOrder = _currentOrder.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _currentUser.value = loginRepository.getCurrentUserInfo()
            _currentUser.value?.let { user ->
                _currentOrder.value = menuItemRemoteDatasource.getOrderNumberOfItems(user.uid)
            }
        }
    }

    override fun updateOrderNumber() {
        CoroutineScope(Dispatchers.IO).launch {
            _currentUser.value?.let { user ->
                _currentOrder.value = menuItemRemoteDatasource.getOrderNumberOfItems(user.uid)
            }
        }
    }
}
