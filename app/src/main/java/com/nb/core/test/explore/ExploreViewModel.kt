package com.nb.core.test.explore

import com.nb.core.RecyclerViewModel
import com.nb.core.test.model.WorksModel

class ExploreViewModel : RecyclerViewModel<WorksModel, String>() {

    override fun onCreateDataSource(parameter: String?) = ExploreDataSource()
}