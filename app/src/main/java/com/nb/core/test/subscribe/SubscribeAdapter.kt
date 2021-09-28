package com.nb.core.test.subscribe

import android.view.ViewGroup
import com.hhh.onepiece.R
import com.nb.core.RecyclerAdapter
import com.nb.core.createView
import com.nb.core.test.model.WorksModel

class SubscribeAdapter : RecyclerAdapter<WorksModel>() {

    override fun getItemViewLayout(position: Int) = R.layout.home_subscribe_item_layout

    override fun getViewHolder(parent: ViewGroup, viewLayout: Int) =
        SubscribeViewHolder(createView(parent, viewLayout))
}