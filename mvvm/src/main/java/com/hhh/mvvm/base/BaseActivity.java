package com.hhh.mvvm.base;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.hhh.mvvm.R;
import com.hhh.mvvm.listener.OnActivityResultListener;
import com.hhh.mvvm.listener.OnBackPressedListener;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

public abstract class BaseActivity extends RxFragmentActivity {

  public static final String open_in_animation = "open_in_animation";
  public static final String open_out_animation = "open_out_animation";
  public static final String close_in_animation = "close_in_animation";
  public static final String close_out_animation = "close_out_animation";

  private final List<OnBackPressedListener> mBackPressedListenerList = new LinkedList<>();

  protected BaseFragment mFragment;

  private OnActivityResultListener mActivityResultListener;
  private int mRequestCode;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (enableTransitionAnim()) {
      Intent intent = getIntent();
      overridePendingTransition(intent.getIntExtra(open_in_animation, R.anim.slide_in_from_right),
          intent.getIntExtra(open_out_animation, R.anim.slide_stay));
    }
    int containerViewId = getContainerViewId();
    FragmentManager fragmentManager = getSupportFragmentManager();
    mFragment = (BaseFragment) fragmentManager.findFragmentById(containerViewId);
    if (mFragment == null) {
      mFragment = onCreateFragment();
      fragmentManager.beginTransaction()
          .replace(containerViewId, mFragment, mFragment.getClass().getName()).commit();
    }
  }

  @IdRes
  protected int getContainerViewId() {
    return android.R.id.content;
  }

  protected boolean enableTransitionAnim() {
    return true;
  }

  @NonNull
  protected abstract BaseFragment onCreateFragment();

  @Override
  public void finish() {
    super.finish();
    if (isTaskRoot()
        && !TextUtils.equals(this.getClass().getName(), BaseUtils.getLaunchActivityName(this))) {
      BaseUtils.startHomeActivity(this);
    }
    if (enableTransitionAnim()) {
      Intent intent = getIntent();
      overridePendingTransition(intent.getIntExtra(close_in_animation, R.anim.slide_stay),
          intent.getIntExtra(close_out_animation, R.anim.slide_out_to_right));
    }
  }

  @Override
  public void onBackPressed() {
    for (OnBackPressedListener listener : mBackPressedListenerList) {
      if (listener.onBackPressed()) {
        return;
      }
    }
    super.onBackPressed();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (mActivityResultListener != null && mRequestCode == requestCode) {
      mActivityResultListener.onActivityResult(requestCode, resultCode, data);
    }
  }

  public void startActivityForCallback(@NonNull Intent intent, int requestCode,
      @NonNull OnActivityResultListener resultListener) {
    startActivityForCallback(intent, requestCode, null, resultListener);
  }

  public void startActivityForCallback(@NonNull Intent intent, int requestCode,
      @Nullable Bundle options, @NonNull OnActivityResultListener resultListener) {
    mRequestCode = requestCode;
    mActivityResultListener = resultListener;
    startActivityForResult(intent, requestCode, options);
  }

  public BaseActivity addBackPressedListener(@NonNull OnBackPressedListener backPressedListener) {
    if (!mBackPressedListenerList.contains(backPressedListener)) {
      mBackPressedListenerList.add(0, backPressedListener);
    }
    return this;
  }

  public BaseActivity removeBackPressedListener(
      @NonNull OnBackPressedListener backPressedListener) {
    mBackPressedListenerList.remove(backPressedListener);
    return this;
  }
}
