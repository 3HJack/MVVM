package com.nb.core

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

open class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view), DefaultLifecycleObserver,
    RecyclerFragmentInjection {

    protected lateinit var recyclerFragment: RecyclerFragment<*, *>

    private val errorMsgView: TextView = itemView.findViewById(R.id.error_msg_view)
    private val progressView: View = itemView.findViewById(R.id.progress_view)
    private val retryView: View = itemView.findViewById(R.id.retry_view)

    init {
        retryView.setOnClickListener { recyclerFragment.recyclerAdapter.retry() }
    }

    override fun inject(fragment: RecyclerFragment<*, *>) {
        recyclerFragment = fragment
    }

    fun bind(loadState: LoadState) {
        progressView.isVisible = loadState is LoadState.Loading
        retryView.isVisible = loadState is LoadState.Error
        errorMsgView.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorMsgView.text = (loadState as? LoadState.Error)?.error?.message
    }
}