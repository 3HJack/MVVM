package com.hhh.onepiece.model;

import com.hhh.mvvm.listener.DiffCallback;

import androidx.annotation.NonNull;

public final class WorksModel implements DiffCallback<WorksModel> {

  public int mId;

  public int mColor;

  public float mAspectRatio;

  public String mText;

  public transient boolean mShown;

  @Override
  public boolean areItemsTheSame(@NonNull WorksModel newItem) {
    return mId == newItem.mId;
  }

  @Override
  public boolean areContentsTheSame(@NonNull WorksModel newItem) {
    return areItemsTheSame(newItem) && mColor == newItem.mColor;
  }
}
