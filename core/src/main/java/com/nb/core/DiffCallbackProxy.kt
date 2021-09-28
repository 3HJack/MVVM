package com.nb.core

import androidx.recyclerview.widget.DiffUtil

class DiffCallbackProxy<MODEL : DiffCallback<MODEL>> : DiffUtil.ItemCallback<MODEL>() {

    override fun areItemsTheSame(oldItem: MODEL, newItem: MODEL) = oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: MODEL, newItem: MODEL) =
        oldItem.areContentsTheSame(newItem)
}