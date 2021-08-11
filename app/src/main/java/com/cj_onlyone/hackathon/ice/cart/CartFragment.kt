package com.cj_onlyone.hackathon.ice.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cj_onlyone.hackathon.ice.R
import com.cj_onlyone.hackathon.ice.util.SharedPreferenceUtil
import kotlinx.coroutines.selects.select

class CartFragment : Fragment() {
    lateinit var cartAdapter: CartAdapter
    val datas = mutableListOf<CartData>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // preference store setup
        val rv_cart : RecyclerView = view.findViewById(R.id.rv_cart)
        cartAdapter = CartAdapter(this.context)
        rv_cart.adapter = cartAdapter

        //button for add to the cart

        val selected = SharedPreferenceUtil.getAll(view.context)
        selected.forEach{i-> Log.v("CART : =--===", i)}
        datas.apply {
                add(CartData(name = "item1"))
        }

        cartAdapter.datas = datas
        cartAdapter.notifyDataSetChanged()


        view.findViewById<View>(R.id.bttn_exit_cart).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_cartFragment_to_category_large_dest)
        )
    }

}
