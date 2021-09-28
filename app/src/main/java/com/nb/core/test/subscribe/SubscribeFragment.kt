package com.nb.core.test.subscribe

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hhh.onepiece.R
import com.nb.core.RecyclerFragment
import com.nb.core.test.model.WorksModel

class SubscribeFragment : RecyclerFragment<WorksModel, String>() {

    override fun onCreateRecyclerAdapter() = SubscribeAdapter()

    override fun onCreateViewModel() = ViewModelProvider(this).get(SubscribeViewModel::class.java)

    override fun onCreateItemDecoration(): RecyclerView.ItemDecoration = DividerItemDecoration(
        requireContext(),
        DividerItemDecoration.VERTICAL
    ).also { it.setDrawable(resources.getDrawable(R.drawable.home_subscribe_divider_line)) }
}