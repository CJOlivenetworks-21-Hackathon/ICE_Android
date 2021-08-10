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
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : Fragment() {
    lateinit var groupAdapter: GroupAdapter
    val datas = mutableListOf<GroupData>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.group_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler(view)
        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
        view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.next_action1, null)
        )
        //TODO END STEP 7.2
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun initRecycler(view:View) {
        val rv_group : RecyclerView = view.findViewById(R.id.rv_group)
        groupAdapter = GroupAdapter(this.context)
        groupAdapter.itemClick = object : GroupAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val switchMaterial : SwitchMaterial = view.findViewById(R.id.sw_group_name)
                val status = switchMaterial.isChecked
                switchMaterial.isChecked = !status
            }
        }
        rv_group.adapter = groupAdapter

        datas.apply {
            add(GroupData(name = "filter-1"))
            add(GroupData(name = "filter-2"))
            add(GroupData(name = "filter-3"))
            add(GroupData(name = "filter-4"))
            add(GroupData(name = "filter-5"))
            add(GroupData(name = "filter-6"))

            groupAdapter.datas = datas
            groupAdapter.notifyDataSetChanged()
        }

    }

}
