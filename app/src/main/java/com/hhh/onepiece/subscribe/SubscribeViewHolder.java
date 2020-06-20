package com.hhh.onepiece.subscribe;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hhh.mvvm.listener.OnImageShowListener;
import com.hhh.mvvm.recycler.RecyclerViewHolder;
import com.hhh.onepiece.R;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.view.NetworkImageView;

public class SubscribeViewHolder extends RecyclerViewHolder<WorksModel> {

  protected NetworkImageView mCoverView;

  public SubscribeViewHolder(@NonNull View itemView) {
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
