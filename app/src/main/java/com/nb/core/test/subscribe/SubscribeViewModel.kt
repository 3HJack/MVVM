package com.nb.core.test.subscribe

import com.nb.core.RecyclerViewModel
import com.nb.core.test.model.WorksModel

class SubscribeViewModel : RecyclerViewModel<WorksModel, String>() {

    override fun onCreateDataSource(parameter: String?) = SubscribeDataSource()
}