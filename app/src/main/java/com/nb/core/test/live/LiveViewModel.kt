package com.nb.core.test.live

import com.nb.core.RecyclerViewModel
import com.nb.core.test.model.WorksModel

class LiveViewModel : RecyclerViewModel<WorksModel, String>() {

    override fun onCreateDataSource(parameter: String?) = LiveDataSource()
}