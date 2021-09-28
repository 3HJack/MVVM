package com.nb.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun attachedToRecyclerView(recyclerView: RecyclerView) {
    val layoutManager = recyclerView.layoutManager
    if (layoutManager is GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = layoutManager.spanCount
        }
    }
}

fun viewAttachedToWindow(holder: RecyclerView.ViewHolder) {
    val layoutParams = holder.itemView.layoutParams
    if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
        layoutParams.isFullSpan = true
    }
}

fun createView(parent: ViewGroup, viewLayout: Int): View =
    LayoutInflater.from(parent.context).inflate(viewLayout, parent, false)