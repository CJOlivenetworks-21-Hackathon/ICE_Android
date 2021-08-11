package com.cj_onlyone.hackathon.ice.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cj_onlyone.hackathon.ice.R
import com.cj_onlyone.hackathon.ice.cart.CartAdapter
import com.cj_onlyone.hackathon.ice.cart.CartData

class CartAdapter (private val context: Context?) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    var datas = mutableListOf<CartData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_cart,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    interface ItemClick {
        fun onClick(view: View, position:Int)
    }
    var itemClick : ItemClick? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.tv_cart_item)

        fun bind(item: CartData) {
            txtName.text = item.name
        }
    }

}