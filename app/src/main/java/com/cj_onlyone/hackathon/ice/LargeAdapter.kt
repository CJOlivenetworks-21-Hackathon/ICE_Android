package com.cj_onlyone.hackathon.ice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LargeAdapter(private val context: Context?) : RecyclerView.Adapter<LargeAdapter.ViewHolder>() {

    var datas = mutableListOf<LargeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_ex,parent,false)
        view.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.next_action2, null)
        )
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

        private val txtName: TextView = itemView.findViewById(R.id.tv_rv_name)
        private val txtAge: TextView = itemView.findViewById(R.id.tv_rv_age)
        private val imgProfile: ImageView = itemView.findViewById(R.id.img_rv_photo)

        fun bind(item: LargeData) {
            txtName.text = item.name
            txtAge.text = item.age.toString()
            Glide.with(itemView).load(item.img).into(imgProfile)
        }
    }

}