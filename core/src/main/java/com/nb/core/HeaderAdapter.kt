package com.nb.core

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class HeaderAdapter : RecyclerView.Adapter<HeaderViewHolder>(), RecyclerFragmentInjection {

    protected lateinit var recyclerFragment: RecyclerFragment<*, *>

    override fun inject(fragment: RecyclerFragment<*, *>) {
        recyclerFragment = fragment
    }

    override fun getItemCount() = 1

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) =
        attachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: HeaderViewHolder) = viewAttachedToWindow(holder)

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) = holder.bind()

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        getViewHolder(parent, viewType).also {
            it.inject(recyclerFragment)
            recyclerFragment.viewLifecycle.addObserver(it)
        }

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder
}