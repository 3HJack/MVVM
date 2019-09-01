package com.hhh.onepiece.view;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hhh.mvvm.listener.OnImageShowListener;

public class NetworkImageView extends SimpleDraweeView implements OnImageShowListener {

  public NetworkImageView(Context context, GenericDraweeHierarchy hierarchy) {
    super(context, hierarchy);
  }

  public NetworkImageView(Context context) {
    super(context);
  }

  public NetworkImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NetworkImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean hadShown() {
    return true;
  }
}
