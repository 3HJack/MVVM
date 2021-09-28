package com.nb.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class PagerViewModel<MODEL> : ViewModel() {

    private val mutableCollectException =
        "If items is a mutable collection, please override getItemId() and containsItem()"

    private val _removeState = MutableLiveData<Long>()
    val removeState: LiveData<Long> = _removeState

    private val _addState = MutableLiveData<Long>()
    val addState: LiveData<Long> = _addState

    private val _updateState = MutableLiveData<Long>()
    val updateState: LiveData<Long> = _updateState

    private val _state = MediatorLiveData<Long>()
    val state: LiveData<Long> = _state

    init {
        _state.addSource(_removeState) { _state.value = it }
        _state.addSource(_addState) { _state.value = it }
        _state.addSource(_updateState) { _state.value = it }
    }

    val size get() = items.size
    protected val items by lazy { onCreateItems() }

    protected abstract fun onCreateItems(): MutableList<MODEL>

    open fun getItemId(position: Int): Long = throw RuntimeException(mutableCollectException)

    open fun containsItem(itemId: Long): Boolean = throw RuntimeException(mutableCollectException)

    fun getItem(position: Int) = items[position]

    fun remove(position: Int) {
        items.removeAt(position)
        _removeState.value = getItemId(position)
    }

    fun add(position: Int, model: MODEL) {
        items.add(position, model)
        _addState.value = getItemId(position)
    }

    fun update(position: Int, model: MODEL) {
        items[position] = model
        _updateState.value = getItemId(position)
    }
}