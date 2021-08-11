package com.cj_onlyone.hackathon.ice.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.cj_onlyone.hackathon.ice.R


class GroupAdapter (private val context: Context?) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    var datas = mutableListOf<GroupData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_group,parent,false)
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

        private val grpView: Switch = itemView.findViewById(R.id.sw_group_name)

        fun bind(item: GroupData) {
            grpView.text = item.name
        }
    }

}