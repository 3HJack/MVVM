package com.nb.core

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class RecyclerDataSource<MODEL : Any, PARAMETER>(private val parameter: PARAMETER?) :
    PagingSource<Int, MODEL>() {

    override fun getRefreshKey(state: PagingState<Int, MODEL>): Int? = state.anchorPosition

}