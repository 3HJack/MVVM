package com.hhh.mvvm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class RefreshFooterView extends FrameLayout implements RefreshFooter {
  public RefreshFooterView(@NonNull Context context) {
    super(context);
  }

  public RefreshFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public RefreshFooterView(@NonNull Context context, @Nullable AttributeSet attrs,
    int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean setNoMoreData(boolean noMoreData) {
    return false;
  }

  @NonNull
  @Override
  public View getView() {
    return this;
  }

  @NonNull
  @Override
  public SpinnerStyle getSpinnerStyle() {
    return SpinnerStyle.Translate;
  }

  @Override
  public void setPrimaryColors(int... colors) {

  }

  @Override
  public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

  }

  @Override
  public void onMoving(boolean isDragging, float percent, int offset, int height,
    int maxDragHeight) {

  }

  @Override
  public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

  }

  @Override
  public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

  }

  @Override
  public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
    return 0;
  }

  @Override
  public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

  }

  @Override
  public boolean isSupportHorizontalDrag() {
    return false;
  }

  @Override
  public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState,
    @NonNull RefreshState newState) {

  }
}
