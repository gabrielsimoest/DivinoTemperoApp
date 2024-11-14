package com.example.kotlinfoodorder.login.data

import android.R
import com.example.kotlinfoodorder.login.data.remote.UserRemoteDatasource
import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.menuManager.ui.menu.MenuItem

class MenuItemRepositoryImpl(
): MenuItemRepository {
    override suspend fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem("Pizza Margherita", "Pizza clássica com molho de tomate, mozzarella e manjericão.", 29.90, R.drawable.ic_menu_report_image),
            MenuItem("Hambúrguer", "Hambúrguer com carne, queijo, alface e molho especial.", 19.90, android.R.drawable.ic_menu_report_image),
            MenuItem("Sushi", "Sushi fresco com peixe de alta qualidade e arroz temperado.", 39.90, android.R.drawable.ic_menu_report_image),
            MenuItem("Espaguete à Bolonhesa", "Massa com molho de carne e tomate, servido com queijo parmesão.", 24.90, android.R.drawable.ic_menu_report_image),
            MenuItem("Coxinha", "Coxinha de frango empanada, crocante por fora e macia por dentro.", 8.90, android.R.drawable.ic_menu_report_image),
            MenuItem("Torta de Chocolate", "Torta de chocolate com recheio cremoso e cobertura de ganache.", 12.90, android.R.drawable.ic_menu_report_image),
            MenuItem("Caipirinha", "Bebida tradicional brasileira feita com cachaça, limão e açúcar.", 15.90, android.R.drawable.ic_menu_report_image),
            MenuItem("Mojito", "Cocktail refrescante com rum, hortelã, limão e soda.", 18.90, android.R.drawable.ic_menu_report_image)
        )
    }

}