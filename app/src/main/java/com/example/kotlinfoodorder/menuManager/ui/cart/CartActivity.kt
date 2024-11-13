package com.example.kotlinfoodorder.menuManager.ui.menu

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodorder.databinding.ActivityCartBinding


class CartActivity : ComponentActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewMenu.layoutManager = GridLayoutManager(this, 1)

        val myItemList = listOf(
            CartItem("Pizza Margherita", "Pizza clássica com molho de tomate, mozzarella e manjericão.", 29.90, R.drawable.ic_menu_report_image),
            CartItem("Hambúrguer", "Hambúrguer com carne, queijo, alface e molho especial.", 19.90, R.drawable.ic_menu_report_image),
            CartItem("Sushi", "Sushi fresco com peixe de alta qualidade e arroz temperado.", 39.90, R.drawable.ic_menu_report_image),
            CartItem("Espaguete à Bolonhesa", "Massa com molho de carne e tomate, servido com queijo parmesão.", 24.90, R.drawable.ic_menu_report_image),
            CartItem("Coxinha", "Coxinha de frango empanada, crocante por fora e macia por dentro.", 8.90, R.drawable.ic_menu_report_image),
            CartItem("Torta de Chocolate", "Torta de chocolate com recheio cremoso e cobertura de ganache.", 12.90, R.drawable.ic_menu_report_image),
            CartItem("Caipirinha", "Bebida tradicional brasileira feita com cachaça, limão e açúcar.", 15.90, R.drawable.ic_menu_report_image),
            CartItem("Mojito", "Cocktail refrescante com rum, hortelã, limão e soda.", 18.90, R.drawable.ic_menu_report_image)
        )

        val adapter = CartAdapter(myItemList)
        binding.recyclerViewMenu.adapter = adapter
    }
}