package com.nb.core.test.live

import android.view.ViewGroup
import com.hhh.onepiece.R
import com.nb.core.RecyclerAdapter
import com.nb.core.createView
import com.nb.core.test.model.WorksModel

class LiveAdapter : RecyclerAdapter<WorksModel>() {

    override fun getItemViewLayout(position: Int) = R.layout.home_live_item_layout

    override fun getViewHolder(parent: ViewGroup, viewLayout: Int) =
        LiveViewHolder(createView(parent, viewLayout))
}