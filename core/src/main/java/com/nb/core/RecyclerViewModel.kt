package com.nb.core

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

abstract class RecyclerViewModel<MODEL : Any, PARAMETER> : ViewModel() {

    protected open fun onCreatePagingConfig() = PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
        prefetchDistance = 4,
        enablePlaceholders = false
    )

    protected abstract fun onCreateDataSource(parameter: PARAMETER?): RecyclerDataSource<MODEL, PARAMETER>

    private val _loadLiveData = MutableLiveData<PARAMETER?>()
    val loadLiveData = _loadLiveData.switchMap {
        Pager(onCreatePagingConfig()) { onCreateDataSource(it) }.flow.asLiveData()
            .cachedIn(viewModelScope)
    }

    fun load(parameter: PARAMETER?) {
        _loadLiveData.value = parameter
    }
}