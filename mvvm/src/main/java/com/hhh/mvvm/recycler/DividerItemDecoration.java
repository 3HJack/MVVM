package com.hhh.mvvm.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

  public static final int HORIZONTAL = 0;
  public static final int VERTICAL = 1;

  private static final String TAG = "DividerItemDecoration";
  private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

  private final Rect mBounds = new Rect();
  private Drawable mDivider;

  private int mOrientation;
  private int mHeadViewId;
  private int mFootViewId;

  public DividerItemDecoration(Context context, int orientation) {
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    mDivider = a.getDrawable(0);
    if (mDivider == null) {
      Log.w(TAG, "@android:attr/listDivider was not set in the theme used for this " +
        "DividerItemDecoration. Please set that attribute all call setDrawable()");
    }
    a.recycle();
    setOrientation(orientation);
  }

  public DividerItemDecoration setOrientation(int orientation) {
    if (orientation != HORIZONTAL && orientation != VERTICAL) {
      throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or "
        + "VERTICAL");
    }
    mOrientation = orientation;
    return this;
  }

  @Nullable
  public Drawable getDrawable() {
    return mDivider;
  }

  public DividerItemDecoration setDrawable(@NonNull Drawable drawable) {
    if (drawable == null) {
      throw new IllegalArgumentException("Drawable cannot be null.");
    }
    mDivider = drawable;
    return this;
  }

  public DividerItemDecoration setHeadViewId(int headViewId) {
    mHeadViewId = headViewId;
    return this;
  }

  public DividerItemDecoration setFootViewId(int footViewId) {
    mFootViewId = footViewId;
    return this;
  }

  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
    @NonNull RecyclerView.State state) {
    if (parent.getLayoutManager() == null || mDivider == null) {
      return;
    }
    if (mOrientation == VERTICAL) {
      drawVertical(c, parent);
    } else {
      drawHorizontal(c, parent);
    }
  }

  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
    @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    if (mDivider == null) {
      outRect.set(0, 0, 0, 0);
      return;
    }
    if (mOrientation == VERTICAL) {
      outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    } else {
      outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
    }
  }

  private void drawVertical(Canvas canvas, RecyclerView parent) {
    canvas.save();
    final int left;
    final int right;
    //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
    if (parent.getClipToPadding()) {
      left = parent.getPaddingLeft();
      right = parent.getWidth() - parent.getPaddingRight();
      canvas.clipRect(left, parent.getPaddingTop(), right,
        parent.getHeight() - parent.getPaddingBottom());
    } else {
      left = 0;
      right = parent.getWidth();
    }

    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      int viewId = child.getId();
      if (viewId == mHeadViewId || viewId == mFootViewId) {
        continue;
      }
      parent.getDecoratedBoundsWithMargins(child, mBounds);
      final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
      final int top = bottom - mDivider.getIntrinsicHeight();
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(canvas);
    }
    canvas.restore();
  }

  private void drawHorizontal(Canvas canvas, RecyclerView parent) {
    canvas.save();
    final int top;
    final int bottom;
    //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
    if (parent.getClipToPadding()) {
      top = parent.getPaddingTop();
      bottom = parent.getHeight() - parent.getPaddingBottom();
      canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
        bottom);
    } else {
      top = 0;
      bottom = parent.getHeight();
    }

    int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      int viewId = child.getId();
      if (viewId == mHeadViewId || viewId == mFootViewId) {
        continue;
      }
      parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
      final int right = mBounds.right + Math.round(child.getTranslationX());
      final int left = right - mDivider.getIntrinsicWidth();
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(canvas);
    }
    canvas.restore();
  }
}
