package com.example.kotlinfoodorder.menu.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinfoodorder.R
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.model.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class MenuAdapter(
    private val menuItemRepository: MenuItemRepository,
    private val onItemClick: (MenuItem) -> Unit,
    private val onAddItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var itemList = listOf<MenuItem>()
    private var filteredItemList = listOf<MenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = filteredItemList[position]
        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        holder.itemPrice.text = currencyFormat.format(item.price)
        val storageReference = FirebaseStorage.getInstance().reference.child(item.imagePath)

        storageReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(holder.imageView.context)
                .load(uri)
                .into(holder.imageView)
        }.addOnFailureListener {
            holder.imageView.setImageResource(R.drawable.ic_food_placeholder)
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        holder.addItemButton.setOnClickListener {
            onAddItemClick(item)
        }
    }

    override fun getItemCount(): Int = filteredItemList.size

    fun loadItems() {
        CoroutineScope(Dispatchers.Main).launch {
            itemList = withContext(Dispatchers.IO) {
                menuItemRepository.getMenuItems()
            }
            filteredItemList = itemList
            notifyDataSetChanged()
        }
    }

    fun filterItems(predicate: (MenuItem) -> Boolean) {
        filteredItemList = itemList.filter(predicate)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val imageView: ImageView = itemView.findViewById(R.id.list_image)
        val addItemButton: FloatingActionButton = itemView.findViewById(R.id.fab_add)
    }
}
