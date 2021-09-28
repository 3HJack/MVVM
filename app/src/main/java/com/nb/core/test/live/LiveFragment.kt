package com.nb.core.test.live

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hhh.onepiece.R
import com.nb.core.HeaderAdapter
import com.nb.core.HeaderViewHolder
import com.nb.core.RecyclerFragment
import com.nb.core.createView
import com.nb.core.test.model.WorksModel

class LiveFragment : RecyclerFragment<WorksModel, String>() {

    override fun onCreateRecyclerAdapter() = LiveAdapter()

    override fun onCreateViewModel() = ViewModelProvider(this).get(LiveViewModel::class.java)

    override fun onCreateHeaderAdapter() = object : HeaderAdapter() {
        override fun getViewHolder(parent: ViewGroup, viewType: Int) =
            object : HeaderViewHolder(createView(parent, R.layout.home_live_head_layout)) {
                override fun bind() {}
            }
    }
}