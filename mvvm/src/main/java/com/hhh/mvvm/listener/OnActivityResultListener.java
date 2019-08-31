package com.hhh.mvvm.listener;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface OnActivityResultListener {
  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
