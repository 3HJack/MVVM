package com.hhh.mvvm.listener;

import com.hhh.mvvm.recycler.ScrollStatus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface OnViewScrollListener {

  void setViewScrollCallback(@Nullable OnViewScrollCallback viewScrollCallback);

  interface OnViewScrollCallback {

    void onViewScroll(@NonNull ScrollStatus scrollStatus);
  }
}
