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

package com.cj_onlyone.hackathon.ice.medium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.cj_onlyone.hackathon.ice.R
import com.cj_onlyone.hackathon.ice.util.SharedPreferenceUtil

class CategoryMediumFragment  : Fragment() {
    lateinit var mediumAdapter: MediumAdapter
    val datas = mutableListOf<MediumData>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.category_medium_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // preference store setup
        val rv_profile : RecyclerView = view.findViewById(R.id.rv_medium)
        mediumAdapter = MediumAdapter(this.context)
        mediumAdapter.itemClick = object : MediumAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.item_click))
                // store selected item info
                val item = view.findViewById<TextView>(R.id.tv_med_name).toString()
                SharedPreferenceUtil.set(view.context, item, true)
            }
        }
        rv_profile.adapter = mediumAdapter

        //button for add to the cart
        val largeCategory : String? = savedInstanceState?.getString("LARGE")
        if (largeCategory!= null) {
            datas.apply {
                add(MediumData(name = largeCategory))
            }
        }
        else {
            datas.apply {
                add(MediumData(name = "item1"))
                add(MediumData(name = "item2"))
            }
        }

        mediumAdapter.datas = datas
        mediumAdapter.notifyDataSetChanged()

        view.findViewById<View>(R.id.next_button2).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.medium_to_large)
        )
    }

}
