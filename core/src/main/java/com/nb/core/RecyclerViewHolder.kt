package com.nb.core

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewHolder<MODEL : DiffCallback<MODEL>>(view: View) :
    RecyclerView.ViewHolder(view), DefaultLifecycleObserver, RecyclerFragmentInjection {

    protected lateinit var model: MODEL
    protected lateinit var recyclerFragment: RecyclerFragment<MODEL, *>

    override fun inject(fragment: RecyclerFragment<*, *>) {
        recyclerFragment = fragment as RecyclerFragment<MODEL, *>
    }

    @CallSuper
    open fun bind(model: MODEL) {
        this.model = model
    }
}