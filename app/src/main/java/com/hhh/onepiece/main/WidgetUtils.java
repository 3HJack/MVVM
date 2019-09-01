package com.hhh.onepiece.main;

import android.content.Context;

import androidx.annotation.NonNull;

public final class WidgetUtils {

  private WidgetUtils() {}

  public static int dip2px(@NonNull Context context, float dpValue) {
    return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
  }
}
