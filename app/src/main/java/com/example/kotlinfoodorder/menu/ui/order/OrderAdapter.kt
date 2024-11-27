package com.example.kotlinfoodorder.menu.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfoodorder.R
import com.example.kotlinfoodorder.login.data.MenuItemRepository
import com.example.kotlinfoodorder.menu.data.repository.OrderRepository
import com.example.kotlinfoodorder.menu.model.OrderItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter(
    private val menuItemRepository: MenuItemRepository,
    private val orderRepository: OrderRepository,
    private val userId: String,
    private val orderTotalTextView: TextView
) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    private var itemList: MutableList<OrderItem> = mutableListOf()
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_menu, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]

        holder.itemName.text = item.name
        holder.itemPrice.text = currencyFormat.format(item.price * item.quantity)
        holder.imageView.setImageResource(item.imageResId)
        holder.itemQuantity.text = item.quantity.toString()

        holder.addButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                orderRepository.addItemToOrder(userId, item.id)

                item.quantity++
                holder.itemQuantity.text = item.quantity.toString()
                holder.itemPrice.text = currencyFormat.format(item.price * item.quantity)
                notifyItemChanged(position)
                updateOrderTotal()
            }
        }

        holder.removeButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                orderRepository.removeItemFromOrder(userId, item.id)

                if (item.quantity > 1) {
                    item.quantity--
                    holder.itemQuantity.text = item.quantity.toString()
                    holder.itemPrice.text = currencyFormat.format(item.price * item.quantity)
                    notifyItemChanged(position)
                } else {
                    itemList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, itemList.size)
                }
                updateOrderTotal()
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun loadItems() {
        CoroutineScope(Dispatchers.Main).launch {
            itemList = withContext(Dispatchers.IO) {
                val itemsInOrder = orderRepository.getOrderItems(userId)
                menuItemRepository.getMenuOrderItems(itemsInOrder)?.filter { it.quantity > 0 }?.toMutableList() ?: mutableListOf()
            }
            notifyDataSetChanged()
            updateOrderTotal()
        }
    }

    private fun updateOrderTotal() {
        val total = itemList.sumOf { it.price * it.quantity }
        orderTotalTextView.text = currencyFormat.format(total)
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
