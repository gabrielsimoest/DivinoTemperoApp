package com.example.kotlinfoodorder.menuManager.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.login.data.MenuCategoryRepository
import com.example.kotlinfoodorder.login.data.UserRepository
import com.example.kotlinfoodorder.login.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val loginRepository: UserRepository,
    private val menuCategoryRepository: MenuCategoryRepository
) : ViewModel() {
    private val _currentCategories = MutableStateFlow<List<MenuCategoryModel>?>(null)
    val currentCategories = _currentCategories.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _uiAction = MutableSharedFlow<MenuAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun onViewCreated() {
        viewModelScope.launch {
            _currentUser.value = loginRepository.getCurrentUserInfo()
            _currentCategories.value = menuCategoryRepository.getMenuCategories()
        }
    }

    fun logout(){
        viewModelScope.launch {
            loginRepository.logout();
            _uiAction.emit(MenuAction.NavigateHome)
        }
    }
}