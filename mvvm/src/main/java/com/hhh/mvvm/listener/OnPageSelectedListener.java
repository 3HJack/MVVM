package com.hhh.mvvm.listener;

public interface OnPageSelectedListener {

  default void onPageSelected() {}

  default void onPageUnselected() {}

  default void onPageReselected() {}
}
