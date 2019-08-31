package com.hhh.mvvm.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.hhh.mvvm.listener.OnViewScrollListener;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class CustomScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior
    implements
      OnViewScrollListener {

  private OnViewScrollCallback mViewScrollCallback;

  public CustomScrollingViewBehavior() {}

  public CustomScrollingViewBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
    if (mViewScrollCallback != null) {
      mViewScrollCallback
          .onViewScroll(new ScrollStatus((int) dependency.getX(), (int) dependency.getY()));
    }
    return super.onDependentViewChanged(parent, child, dependency);
  }

  @Override
  public void setViewScrollCallback(@Nullable OnViewScrollCallback viewScrollCallback) {
    mViewScrollCallback = viewScrollCallback;
  }
}
