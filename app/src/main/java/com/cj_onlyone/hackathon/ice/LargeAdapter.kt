package com.cj_onlyone.hackathon.ice

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LargeAdapter(private val context: Context?) : RecyclerView.Adapter<LargeAdapter.ViewHolder>() {

    var datas = mutableListOf<LargeData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_large,parent,false)
        /*
        view.setOnClickListener { view ->
                val target : String = view.findViewById<TextView>(R.id.tv_rv_name).toString()
                val bundle = Bundle()
                bundle.putString("Large", target)
                Navigation.createNavigateOnClickListener(R.id.next_action2, null)

        }
        */
        /*
        view.setOnClickListener (
                Navigation.createNavigateOnClickListener(R.id.next_action2, null)
        )
         */
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    interface ItemClick {
        fun onClick(view: View, position:Int)
    }
    var itemClick : ItemClick? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            val target : LargeData = datas[position]
            val bundle = Bundle()
            bundle.putString("LARGE", target.name)

            Toast.makeText(holder.itemView.context, target.name, Toast.LENGTH_LONG).show()
            // Using the Kotlin extension in the -ktx artifacts
            // Alternatively, use Navigation.findNavController(holder.itemView)
            holder.itemView.findNavController().navigate(
                    R.id.next_action2, bundle)
        }
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