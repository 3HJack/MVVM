package com.nb.core.test.explore

import android.view.ViewGroup
import com.hhh.onepiece.R
import com.nb.core.RecyclerAdapter
import com.nb.core.createView
import com.nb.core.test.model.WorksModel

class ExploreAdapter : RecyclerAdapter<WorksModel>() {

    override fun getItemViewLayout(position: Int) = R.layout.home_explore_item_layout

    override fun getViewHolder(parent: ViewGroup, viewLayout: Int) =
        ExploreViewHolder(createView(parent, viewLayout))
}