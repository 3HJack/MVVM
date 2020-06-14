package com.hhh.mvvm.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhh.mvvm.listener.OnActivityResultListener;
import com.hhh.mvvm.listener.OnPageSelectedListener;
import com.trello.rxlifecycle3.components.support.RxFragment;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseFragment extends RxFragment implements OnPageSelectedListener {

  public BaseFragment() {
  }

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

  @LayoutRes
  protected abstract int getLayoutResId();

  @Nullable
  public final <T extends View> T findViewById(@IdRes int id) {
    return getView() != null ? getView().findViewById(id) : null;
  }

  public final void finish() {
    if (isAdded()) {
      getActivity().finish();
    }
  }

  public final void onBackPressed() {
    if (isAdded()) {
      getActivity().onBackPressed();
    }
  }

  public final void startActivityForCallback(@NonNull Intent intent, int requestCode,
                                             @NonNull OnActivityResultListener resultListener) {
    startActivityForCallback(intent, requestCode, null, resultListener);
  }

  public final void startActivityForCallback(@NonNull Intent intent, int requestCode,
                                             @Nullable Bundle options, @NonNull OnActivityResultListener resultListener) {
    if (isAdded()) {
      BaseActivity activity = (BaseActivity) getActivity();
      activity.startActivityForCallback(intent, requestCode, options, resultListener);
    }
  }
}
