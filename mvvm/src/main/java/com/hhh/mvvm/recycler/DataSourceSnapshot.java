package com.hhh.mvvm.recycler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataSourceSnapshot<PAGE extends RecyclerResponse<MODEL>, MODEL> {

  final List<MODEL> mModels = new ArrayList<>();
  PAGE mPage;
  boolean mRemovingItem;
  boolean mInsertingItem;
  boolean mUpdatingItem;

  @NonNull
  public ArrayList<MODEL> getModels() {
    return new ArrayList<>(mModels);
  }

  @Nullable
  public PAGE getPage() {
    return mPage;
  }
}
