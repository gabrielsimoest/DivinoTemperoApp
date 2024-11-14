package com.example.kotlinfoodorder.menuManager.ui.menu

sealed class MenuAction {
    data object NavigateHome : MenuAction()
    data class ShowErrorMessage(val message: String?) : MenuAction()
}
