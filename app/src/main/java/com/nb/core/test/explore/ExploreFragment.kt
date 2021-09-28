package com.nb.core.test.explore

import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hhh.mvvm.recycler.StaggeredGridItemDecoration
import com.hhh.onepiece.R
import com.nb.core.HeaderAdapter
import com.nb.core.HeaderViewHolder
import com.nb.core.RecyclerFragment
import com.nb.core.createView
import com.nb.core.test.main.dip2px
import com.nb.core.test.model.WorksModel

class ExploreFragment : RecyclerFragment<WorksModel, String>() {

    override fun onCreateRecyclerAdapter() = ExploreAdapter()

    override fun onCreateViewModel() = ViewModelProvider(this).get(ExploreViewModel::class.java)

    override fun onCreateLayoutManager() =
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    override fun onCreateItemDecoration(): RecyclerView.ItemDecoration? {
        val space = dip2px(requireContext(), 6f)
        return StaggeredGridItemDecoration(space, space, dip2px(requireContext(), 8.5f))
    }

    override fun onCreateHeaderAdapter() = object : HeaderAdapter() {
        override fun getViewHolder(parent: ViewGroup, viewType: Int) =
            object : HeaderViewHolder(createView(parent, R.layout.home_new_explore_head_layout)) {
                override fun bind() {}
            }
    }
}