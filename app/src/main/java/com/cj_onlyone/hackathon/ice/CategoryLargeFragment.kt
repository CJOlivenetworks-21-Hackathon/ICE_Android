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

package com.cj_onlyone.hackathon.ice

import android.os.Bundle
import android.util.Log
//import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

/**
 * Presents how multiple steps flow could be implemented.
 */
class CategoryLargeFragment : Fragment() {

    companion object {
        lateinit var  prefs : PreferenceUtil
    }

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
        //setup to read for cart
        prefs = PreferenceUtil(view.context)
        initRecycler(view)
        val fab: View = view.findViewById(R.id.cart)
        fab.setOnClickListener{view ->

            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }
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
            add(LargeData(img=R.drawable.eggs,  name = "eggs", age = 24))
            add(LargeData(img=R.drawable.garlic,  name = "garlic", age = 24))
            add(LargeData(img=R.drawable.meats,  name = "meats", age = 24))
            add(LargeData(img=R.drawable.nuts,  name = "nuts", age = 24))
            add(LargeData(img=R.drawable.seafoods,   name = "seafoods", age = 24))
            add(LargeData(img=R.drawable.spices,  name = "spices", age = 24))
            largeAdapter.datas = datas
            largeAdapter.notifyDataSetChanged()
        }

    }
}
