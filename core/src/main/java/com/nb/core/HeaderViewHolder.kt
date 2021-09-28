package com.nb.core

import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.RecyclerView

abstract class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view),
    DefaultLifecycleObserver, RecyclerFragmentInjection {

    protected lateinit var recyclerFragment: RecyclerFragment<*, *>

    override fun inject(fragment: RecyclerFragment<*, *>) {
        recyclerFragment = fragment
    }

    abstract fun bind()
}