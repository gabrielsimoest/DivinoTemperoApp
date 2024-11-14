package com.example.kotlinfoodorder.menuManager.ui.menuDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.authManager.ui.login.LoginAction
import com.example.kotlinfoodorder.authManager.ui.login.LoginUserModel
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuDetailViewModel(
    private val menuItemRepository: MenuItemRepository
) : ViewModel() {
    private val _currentMenuItem = MutableStateFlow<MenuItem?>(null)
    val currentMenuItem = _currentMenuItem.asStateFlow()

    fun onAddOrderClicked() {

    }

    fun loadMenuItem(id: Int) {
        viewModelScope.launch {
            _currentMenuItem.value = menuItemRepository.getMenuItem(id)
        }
    }
}