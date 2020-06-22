package com.hhh.mvvm.recycler;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class StaggeredGridItemDecoration extends RecyclerView.ItemDecoration {

  private final int mSide;
  private final int mMiddle;
  private final int mTop;

  public StaggeredGridItemDecoration(int side, int middle, int top) {
    mSide = side;
    mMiddle = middle;
    mTop = top;
  }

  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
    @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    StaggeredGridLayoutManager layoutManager =
      (StaggeredGridLayoutManager) parent.getLayoutManager();
    StaggeredGridLayoutManager.LayoutParams layoutParams =
      (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
    int spanCount = layoutManager.getSpanCount();
    if (layoutParams.isFullSpan()) {
      outRect.set(0, 0, 0, 0);
      return;
    }
    int halfMiddle = mMiddle >> 1;
    int position = parent.getChildViewHolder(view).getBindingAdapterPosition();
    if (position < spanCount) {
      outRect.top = mTop;
    } else {
      outRect.top = halfMiddle;
    }
    int spanIndex = layoutParams.getSpanIndex();
    if (spanIndex == 0) {
      outRect.left = mSide;
      outRect.right = halfMiddle;
    } else if (spanIndex < spanCount - 1) {
      outRect.left = halfMiddle;
      outRect.right = halfMiddle;
    } else {
      outRect.left = halfMiddle;
      outRect.right = mSide;
    }
    outRect.bottom = halfMiddle;
  }
}
