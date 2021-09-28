package com.nb.core

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

abstract class RecyclerAdapter<MODEL : DiffCallback<MODEL>> :
    PagingDataAdapter<MODEL, RecyclerViewHolder<MODEL>>(DiffCallbackProxy()),
    RecyclerFragmentInjection {

    protected lateinit var recyclerFragment: RecyclerFragment<MODEL, *>

    protected abstract fun getItemViewLayout(position: Int): Int

    protected abstract fun getViewHolder(
        parent: ViewGroup,
        viewLayout: Int
    ): RecyclerViewHolder<MODEL>

    override fun inject(fragment: RecyclerFragment<*, *>) {
        recyclerFragment = fragment as RecyclerFragment<MODEL, *>
    }

    final override fun getItemViewType(position: Int) = getItemViewLayout(position)

    final override fun onBindViewHolder(holder: RecyclerViewHolder<MODEL>, position: Int) =
        holder.bind(getItem(position)!!)

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        getViewHolder(parent, viewType).also {
            it.inject(recyclerFragment)
            recyclerFragment.viewLifecycle.addObserver(it)
        }

    fun remove(position: Int) {

    }

    fun insert(position: Int, model: MODEL) {

    }

    fun update(position: Int, model: MODEL) {

    }

    fun clear() {

    }
}