package com.hhh.onepiece.main;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.hhh.mvvm.base.BaseActivity;
import com.hhh.mvvm.base.BaseFragment;

public class HomeActivity extends BaseActivity {

  @NonNull
  @Override
  protected BaseFragment onCreateFragment() {
    return new HomeFragment();
  }

  @Override
  protected boolean enableTransitionAnim() {
    return false;
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    if (intent.getData() != null) {
      ((HomeFragment) mFragment).gotoSpecialFragment(1);
    }
  }
}
