package com.hhh.onepiece.live;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.hhh.mvvm.recycler.RecyclerViewHolder;
import com.hhh.onepiece.R;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.view.NetworkImageView;

public class LiveViewHolder extends RecyclerViewHolder<WorksModel> {

  private NetworkImageView mCoverView;

  public LiveViewHolder(@NonNull View itemView) {
    super(itemView);
    mCoverView = findViewById(R.id.cover);
  }

  @Override
  public void bind(@NonNull WorksModel worksModel) {
    super.bind(worksModel);
    mCoverView.getHierarchy().setPlaceholderImage(new ColorDrawable(worksModel.mColor));
  }
}
