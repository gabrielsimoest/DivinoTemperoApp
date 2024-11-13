package com.example.kotlinfoodorder.menuManager.ui.menu

import android.R.color
import android.R.drawable
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinfoodorder.databinding.ActivityMenuBinding
import com.example.kotlinfoodorder.authManager.ui.login.LoginActivity
import com.example.kotlinfoodorder.menuManager.ui.menuDetail.MenuDetailActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth


class MenuActivity : ComponentActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewMenu.layoutManager = GridLayoutManager(this, 2)

        val userName = auth.currentUser?.displayName ?: "Usuário"
        binding.infoTextPrimary.text = "Olá, $userName"

        initCartButtonListener()
        addFilterButtons()

        val myItemList = listOf(
            Item("Pizza Margherita", "Pizza clássica com molho de tomate, mozzarella e manjericão.", 29.90, drawable.ic_menu_report_image),
            Item("Hambúrguer", "Hambúrguer com carne, queijo, alface e molho especial.", 19.90, drawable.ic_menu_report_image),
            Item("Sushi", "Sushi fresco com peixe de alta qualidade e arroz temperado.", 39.90, drawable.ic_menu_report_image),
            Item("Espaguete à Bolonhesa", "Massa com molho de carne e tomate, servido com queijo parmesão.", 24.90, drawable.ic_menu_report_image),
            Item("Coxinha", "Coxinha de frango empanada, crocante por fora e macia por dentro.", 8.90, drawable.ic_menu_report_image),
            Item("Torta de Chocolate", "Torta de chocolate com recheio cremoso e cobertura de ganache.", 12.90, drawable.ic_menu_report_image),
            Item("Caipirinha", "Bebida tradicional brasileira feita com cachaça, limão e açúcar.", 15.90, drawable.ic_menu_report_image),
            Item("Mojito", "Cocktail refrescante com rum, hortelã, limão e soda.", 18.90, drawable.ic_menu_report_image)
        )

        val adapter = MenuAdapter(myItemList) { item ->
            val intent = Intent(this, MenuDetailActivity::class.java)
//            intent.putExtra("itemName", item.name)
            startActivity(intent)
        }
        binding.recyclerViewMenu.adapter = adapter

        binding.logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun addFilterButtons() {
        val buttonLabels = listOf("Todos", "Comidas", "Bebidas", "Sobremesas", "Outros")

        buttonLabels.forEach { label ->
            val button = MaterialButton(this).apply {
                text = label
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 0, 16, 0)
                }
                setTextColor(ContextCompat.getColor(this@MenuActivity, color.white))
            }
            binding.filterButtonsLayoutContainer.addView(button)
        }
    }

    private fun initCartButtonListener() {
        binding.cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }
}