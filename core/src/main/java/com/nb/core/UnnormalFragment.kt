package com.nb.core

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState

open class UnnormalFragment : BaseFragment() {

    override val layoutId = R.layout.unnormal_layout
    protected lateinit var recyclerFragment: RecyclerFragment<*, *>

    open var loadState: LoadState = LoadState.NotLoading(false)
        set(loadState) {
            if (field == loadState) return
            if (view != null) {
                // TODO: 2021/9/28 The display status of error and empty should be different, and the solution needs to be interactive.
                requireView().isVisible =
                    ((loadState is LoadState.Error) || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)) && recyclerFragment.recyclerAdapter.itemCount == 0
            }
            field = loadState
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerFragment = parentFragment as RecyclerFragment<*, *>
        view.findViewById<View>(R.id.retry_view)
            .setOnClickListener { recyclerFragment.recyclerAdapter.retry() }
    }
}