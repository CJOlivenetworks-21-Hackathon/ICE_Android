/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cj_onlyone.hackathon.ice.large

import android.os.Bundle
//import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cj_onlyone.hackathon.ice.R
import com.cj_onlyone.hackathon.ice.util.SharedPreferenceUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Presents how multiple steps flow could be implemented.
 */
class CategoryLargeFragment : Fragment() {
    lateinit var largeAdapter: LargeAdapter
    val datas = mutableListOf<LargeData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.category_large_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize recycler view
        initRecycler(view)

        // floating button for cart
        val value = SharedPreferenceUtil.get(view.context, "total", 0)

        view.findViewById<FloatingActionButton>(R.id.cart).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.large_to_cart)
        )

        // append listener for save button
        view.findViewById<View>(R.id.bttn_save).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.large_to_setting)
        )
    }


    private fun initRecycler(view:View) {
        val rv_profile : RecyclerView = view.findViewById(R.id.rv_profile)
        largeAdapter = LargeAdapter(this.context)
        largeAdapter.itemClick = object : LargeAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
            }
        }
        rv_profile.adapter = largeAdapter

        datas.apply {
            add(LargeData(img= R.drawable.eggs,  name = "eggs"))
            add(LargeData(img= R.drawable.garlic,  name = "garlic"))
            add(LargeData(img= R.drawable.meats,  name = "meats"))
            add(LargeData(img= R.drawable.nuts,  name = "nuts"))
            add(LargeData(img= R.drawable.seafoods,   name = "seafoods"))
            add(LargeData(img= R.drawable.spices,  name = "spices"))
            largeAdapter.datas = datas
            largeAdapter.notifyDataSetChanged()
        }

    }
}
