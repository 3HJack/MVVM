package com.hhh.mvvm.recycler;

import android.os.Bundle;
import android.view.View;

import com.hhh.mvvm.R;
import com.hhh.mvvm.base.BaseFragment;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class DefaultEmptyFragment extends BaseFragment {
  @Override
  protected int getLayoutResId() {
    return R.layout.universal_empty_layout;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RecyclerFragment recyclerFragment = (RecyclerFragment) getParentFragment();
    RecyclerViewModel viewModel = recyclerFragment.getViewModel();
    viewModel.getLoadingStateLiveData().observe(this, (Observer<LoadingStatus>) loadingStatus -> {
      if (loadingStatus.mStatus == LoadingStatus.Status.FAILED) {
        getView().setVisibility(View.VISIBLE);
      } else {
        getView().setVisibility(View.INVISIBLE);
      }
    });
    findViewById(R.id.retry).setOnClickListener(v -> viewModel.retry());
  }
}
