package com.example.kotlinfoodorder.menuManager.data.repository

import android.R
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuItem

class MenuItemRepositoryImpl(
): MenuItemRepository {
    val inMemoryTempList = listOf(
        MenuItem(1, 2,"Pizza Margherita", "Pizza clássica com molho de tomate, mozzarella e manjericão.", 29.90, R.drawable.ic_menu_report_image),
        MenuItem(2, 2, "Hambúrguer", "Hambúrguer com carne, queijo, alface e molho especial.", 19.90, android.R.drawable.ic_menu_report_image),
        MenuItem(3, 2, "Sushi", "Sushi fresco com peixe de alta qualidade e arroz temperado.", 39.90, android.R.drawable.ic_menu_report_image),
        MenuItem(4, 2, "Espaguete à Bolonhesa", "Massa com molho de carne e tomate, servido com queijo parmesão.", 24.90, android.R.drawable.ic_menu_report_image),
        MenuItem(5, 2, "Coxinha", "Coxinha de frango empanada, crocante por fora e macia por dentro.", 8.90, android.R.drawable.ic_menu_report_image),
        MenuItem(6, 2, "Torta de Chocolate", "Torta de chocolate com recheio cremoso e cobertura de ganache.", 12.90, android.R.drawable.ic_menu_report_image),
        MenuItem(7, 3, "Caipirinha", "Bebida tradicional brasileira feita com cachaça, limão e açúcar.", 15.90, android.R.drawable.ic_menu_report_image),
        MenuItem(8, 3, "Mojito", "Cocktail refrescante com rum, hortelã, limão e soda.", 18.90, android.R.drawable.ic_menu_report_image)
    )

    override suspend fun getMenuItems(): List<MenuItem> {
        return inMemoryTempList;
    }

    override suspend fun getMenuItem(id: Int): MenuItem {
        return inMemoryTempList.find { it.id == id } ?: throw NoSuchElementException("Item with ID $id not found.")
    }
}