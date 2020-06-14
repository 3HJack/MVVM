package com.hhh.onepiece.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hhh.onepiece.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomScrollViewPager extends ViewPager {

  private boolean mScrollable;
  private boolean mSmoothScrollable;

  public CustomScrollViewPager(@NonNull Context context) {
    super(context);
    init(context, null);
  }

  public CustomScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    return mScrollable && super.onTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return mScrollable && super.onInterceptTouchEvent(ev);
  }

  @Override
  public void setCurrentItem(int item) {
    if (!mSmoothScrollable) {
      super.setCurrentItem(item, false);
    } else {
      super.setCurrentItem(item);
    }
  }

  @Override
  public void setCurrentItem(int item, boolean smoothScroll) {
    if (!mSmoothScrollable) {
      super.setCurrentItem(item, false);
    } else {
      super.setCurrentItem(item, smoothScroll);
    }
  }

  public CustomScrollViewPager setScrollable(boolean scrollable) {
    mScrollable = scrollable;
    return this;
  }

  public CustomScrollViewPager setSmoothScrollable(boolean smoothScrollable) {
    mSmoothScrollable = smoothScrollable;
    return this;
  }

  private void init(Context context, AttributeSet attrs) {
    TypedArray array =
        context.obtainStyledAttributes(attrs, R.styleable.CustomScrollViewPager, 0, 0);
    mScrollable = array.getBoolean(R.styleable.CustomScrollViewPager_scrollable, true);
    mSmoothScrollable = array.getBoolean(R.styleable.CustomScrollViewPager_smooth_scrollable, true);
    array.recycle();
  }
}
