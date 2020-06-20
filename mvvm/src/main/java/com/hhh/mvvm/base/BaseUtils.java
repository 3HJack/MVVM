package com.hhh.mvvm.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public final class BaseUtils {

  private static String LAUNCH_ACTIVITY_NAME;

  private BaseUtils() {
  }

  /**
   * 从 FragmentPagerAdapter 拷贝出来的代码
   */
  public static String makeFragmentName(int viewId, long id) {
    return "android:switcher:" + viewId + ":" + id;
  }

  @Nullable
  public static BaseFragment findFragmentByTag(@NonNull BaseActivity activity,
      @NonNull String tag) {
    List<Fragment> fragments = activity.getSupportFragmentManager().getFragments();
    for (Fragment fragment : fragments) {
      if (TextUtils.equals(fragment.getTag(), tag)) {
        return (BaseFragment) fragment;
      }
      BaseFragment childFragment = findFragmentByTag(fragment, tag);
      if (childFragment != null) {
        return childFragment;
      }
    }
    return null;
  }

  @Nullable
  public static BaseFragment findFragmentByTag(@NonNull Fragment fragment, @NonNull String tag) {
    List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();
    for (Fragment childFragment : fragments) {
      if (TextUtils.equals(childFragment.getTag(), tag)) {
        return (BaseFragment) childFragment;
      }
      BaseFragment tempFragment = findFragmentByTag(childFragment, tag);
      if (tempFragment != null) {
        return tempFragment;
      }
    }
    return null;
  }

  @NonNull
  public static String emptyIfNull(@Nullable String text) {
    return text == null ? "" : text;
  }

  public static <T> boolean isEmpty(@Nullable List<T> list) {
    return list == null || list.isEmpty();
  }

  @Nullable
  public static String getLaunchActivityName(@NonNull Context context) {
    if (!TextUtils.isEmpty(LAUNCH_ACTIVITY_NAME)) {
      return LAUNCH_ACTIVITY_NAME;
    }
    Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    ComponentName componentName = intent.getComponent();
    if (componentName != null) {
      LAUNCH_ACTIVITY_NAME = componentName.getClassName();
    }
    return LAUNCH_ACTIVITY_NAME;
  }

  public static void startHomeActivity(@NonNull Context context) {
    Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    context.startActivity(intent);
  }
}
