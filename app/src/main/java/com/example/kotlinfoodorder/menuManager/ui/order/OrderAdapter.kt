package com.example.kotlinfoodorder.menuManager.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfoodorder.R

class OrderAdapter(private val itemList: List<OrderItem>) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_menu, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.itemName.text = item.name
        holder.itemPrice.text = "R$ ${item.price * item.quantity}"
        holder.imageView.setImageResource(item.imageResId)
        holder.itemQuantity.text = item.quantity.toString()

        holder.addButton.setOnClickListener {
            item.quantity++
            holder.itemQuantity.text = item.quantity.toString()
            holder.itemPrice.text = "R$ ${item.price * item.quantity}"
            notifyItemChanged(position)
        }

        holder.removeButton.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                holder.itemQuantity.text = item.quantity.toString()
                holder.itemPrice.text = "R$ ${item.price * item.quantity}"
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val imageView: ImageView = itemView.findViewById(R.id.itemImage)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        val addButton: ImageButton = itemView.findViewById(R.id.addButton)
        val removeButton: ImageButton = itemView.findViewById(R.id.removeButton)
    }
}