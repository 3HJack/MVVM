package com.hhh.onepiece.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hhh.onepiece.R;

public class BannerView<T> extends ConvenientBanner<T> {
  public BannerView(Context context) {
    super(context);
  }

  public BannerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @NonNull
  public RecyclerView getRecyclerView() {
    return findViewById(R.id.cbLoopViewPager);
  }

  @NonNull
  public LinearLayout getIndicatorView() {
    return findViewById(R.id.loPageTurningPoint);
  }
}
