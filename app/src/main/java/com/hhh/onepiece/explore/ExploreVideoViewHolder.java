package com.hhh.onepiece.explore;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.hhh.mvvm.listener.OnImageShowListener;
import com.hhh.mvvm.recycler.RecyclerViewHolder;
import com.hhh.onepiece.R;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.view.NetworkImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExploreVideoViewHolder extends RecyclerViewHolder<WorksModel> {

  private NetworkImageView mCoverView;

  public ExploreVideoViewHolder(@NonNull View itemView) {
    super(itemView);
    mCoverView = findViewById(R.id.cover);
  }

  @Nullable
  @Override
  public OnImageShowListener getImageShowListener() {
    return mCoverView;
  }

  @Override
  public void bind(@NonNull WorksModel worksModel) {
    super.bind(worksModel);
    mCoverView.setAspectRatio(worksModel.mAspectRatio);
    mCoverView.getHierarchy().setPlaceholderImage(new ColorDrawable(worksModel.mColor));
  }
}
