package com.cj_onlyone.hackathon.ice.medium

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cj_onlyone.hackathon.ice.R

class MediumAdapter(private val context: Context?)  : RecyclerView.Adapter<MediumAdapter.ViewHolder>() {

    var datas = mutableListOf<MediumData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_medium,parent,false)
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

        private val txtName: TextView = itemView.findViewById(R.id.tv_med_name)

        fun bind(item: MediumData) {
            txtName.text = item.name
        }
    }

}