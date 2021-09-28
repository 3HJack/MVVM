package com.nb.core

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

open class FooterAdapter : LoadStateAdapter<FooterViewHolder>(), RecyclerFragmentInjection {

    protected lateinit var recyclerFragment: RecyclerFragment<*, *>

    override fun inject(fragment: RecyclerFragment<*, *>) {
        recyclerFragment = fragment
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) =
        attachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: FooterViewHolder) = viewAttachedToWindow(holder)

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        FooterViewHolder(createView(parent, R.layout.footer_layout)).also {
            it.inject(recyclerFragment)
            recyclerFragment.viewLifecycle.addObserver(it)
        }
}