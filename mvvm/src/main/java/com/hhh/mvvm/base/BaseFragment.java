package com.hhh.mvvm.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hhh.mvvm.listener.OnActivityResultListener;
import com.hhh.mvvm.listener.OnPageSelectedListener;
import com.trello.rxlifecycle3.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment implements OnPageSelectedListener {

  public BaseFragment() {
  }

  @LayoutRes
  protected abstract int getLayoutResId();

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
    @Nullable Bundle savedInstanceState) {
    int layoutId = getLayoutResId();
    if (layoutId != 0) {
      return inflater.inflate(layoutId, container, false);
    }
    return null;
  }

  @Nullable
  public final <T extends View> T findViewById(@IdRes int id) {
    return getView() != null ? getView().findViewById(id) : null;
  }

  public final void finish() {
    if (isAdded()) {
      requireActivity().finish();
    }
  }

  public final void onBackPressed() {
    if (isAdded()) {
      requireActivity().onBackPressed();
    }
  }

  public final void startActivityForCallback(@NonNull Intent intent, int requestCode,
    @NonNull OnActivityResultListener resultListener) {
    startActivityForCallback(intent, requestCode, null, resultListener);
  }

  public final void startActivityForCallback(@NonNull Intent intent, int requestCode,
    @Nullable Bundle options, @NonNull OnActivityResultListener resultListener) {
    if (isAdded()) {
      BaseActivity activity = (BaseActivity) requireActivity();
      activity.startActivityForCallback(intent, requestCode, options, resultListener);
    }
  }
}
