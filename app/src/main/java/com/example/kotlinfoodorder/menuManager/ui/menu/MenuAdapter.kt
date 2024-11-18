package com.example.kotlinfoodorder.menuManager.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfoodorder.R
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuAdapter(
    private val menuItemRepository: MenuItemRepository,
    private val onItemClick: (MenuItem) -> Unit,
    private val onAddItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {

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
        holder.itemPrice.text = "R$ ${item.price}"
        holder.imageView.setImageResource(item.imageResId)

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
