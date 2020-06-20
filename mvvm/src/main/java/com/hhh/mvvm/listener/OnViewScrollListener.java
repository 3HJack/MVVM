package com.hhh.mvvm.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hhh.mvvm.recycler.ScrollStatus;

public interface OnViewScrollListener {

  void setViewScrollCallback(@Nullable OnViewScrollCallback viewScrollCallback);

  interface OnViewScrollCallback {

    void onViewScroll(@NonNull ScrollStatus scrollStatus);
  }
}
