package com.nb.core.test.model

import com.nb.core.DiffCallback

data class WorksModel(val id: Int, val color: Int, val aspectRatio: Float, val text: String) :
    DiffCallback<WorksModel> {

    override fun areItemsTheSame(newItem: WorksModel): Boolean {
        return id == newItem.id
    }

    override fun areContentsTheSame(newItem: WorksModel): Boolean {
        return areItemsTheSame(newItem) && color == newItem.color
    }

}
