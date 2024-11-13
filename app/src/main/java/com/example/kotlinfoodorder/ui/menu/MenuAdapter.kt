package com.example.kotlinfoodorder.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfoodorder.R

class MenuAdapter(
    private val itemList: List<Item>,
    private val onItemClick: (Item) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemName.text = item.name
        holder.itemDescription.text = item.description
        holder.itemPrice.text = "R$ ${item.price}"
        holder.imageView.setImageResource(item.imageResId)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val imageView: ImageView = itemView.findViewById(R.id.list_image)
    }
}
