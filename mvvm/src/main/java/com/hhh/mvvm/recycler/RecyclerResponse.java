package com.hhh.mvvm.recycler;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.Nullable;

public abstract class RecyclerResponse<MODEL> {

  public static final String NO_MORE = "no_more";

  @SerializedName("pcursor")
  public String mCursor;

  @Nullable
  public abstract List<MODEL> getItems();

  public boolean hasMore() {
    return !TextUtils.isEmpty(mCursor) && !NO_MORE.equals(mCursor);
  }

  @Nullable
  public String getNextPageKey() {
    return !TextUtils.equals(NO_MORE, mCursor) ? mCursor : null;
  }

  public boolean hasPreviousMore() {
    return false;
  }

  @Nullable
  public String getPreviousPageKey() {
    return null;
  }
}
