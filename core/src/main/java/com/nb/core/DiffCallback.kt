package com.nb.core

interface DiffCallback<MODEL> {

    fun areItemsTheSame(newItem: MODEL): Boolean

    fun areContentsTheSame(newItem: MODEL): Boolean
}