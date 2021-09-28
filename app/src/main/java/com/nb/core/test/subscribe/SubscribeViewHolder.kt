package com.nb.core.test.subscribe

import android.graphics.drawable.ColorDrawable
import android.view.View
import com.facebook.drawee.view.SimpleDraweeView
import com.hhh.onepiece.R
import com.nb.core.RecyclerViewHolder
import com.nb.core.test.model.WorksModel

class SubscribeViewHolder(view: View) : RecyclerViewHolder<WorksModel>(view) {

    private val coverView: SimpleDraweeView = itemView.findViewById(R.id.cover)

    override fun bind(model: WorksModel) {
        super.bind(model)
        coverView.aspectRatio = model.aspectRatio
        coverView.hierarchy.setPlaceholderImage(ColorDrawable(model.color))
    }
}