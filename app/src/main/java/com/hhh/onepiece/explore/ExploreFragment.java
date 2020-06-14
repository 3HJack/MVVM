package com.hhh.onepiece.explore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.recycler.DiffCallbackProxy;
import com.hhh.mvvm.recycler.RecyclerFragment;
import com.hhh.mvvm.recycler.RecyclerPagedListAdapter;
import com.hhh.mvvm.recycler.RecyclerViewModel;
import com.hhh.mvvm.recycler.StaggeredGridItemDecoration;
import com.hhh.onepiece.main.WidgetUtils;
import com.hhh.onepiece.model.WorksModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ExploreFragment extends RecyclerFragment<WorksModel, String> {

  @NonNull
  @Override
  protected RecyclerView.LayoutManager onCreateLayoutManager() {
    return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
  }

  @NonNull
  @Override
  protected RecyclerView.ItemDecoration onCreateItemDecoration() {
    int space = WidgetUtils.dip2px(getContext(), 6f);
    return new StaggeredGridItemDecoration(space, space, WidgetUtils.dip2px(getContext(), 8.5f));
  }

  @NonNull
  @Override
  protected RecyclerPagedListAdapter<WorksModel> onCreateAdapter() {
    return new ExplorePagedAdapter(new DiffCallbackProxy<>());
  }

  @NonNull
  @Override
  protected RecyclerViewModel<WorksModel, String> onCreateViewModel() {
    return new ViewModelProvider(this).get(ExploreViewModel.class);
  }

  @Nullable
  @Override
  protected String onCreateParameter() {
    return "";
  }

  @Nullable
  @Override
  protected BaseFragment onCreateHeadFragment() {
    return new ExploreHeadFragment();
  }

  @Override
  protected boolean hasFixedSize() {
    return false;
  }

  @Override
  protected int getModelShowType() {
    return MODEL_SHOW_TYPE_COVER;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewModel.getModelShowLiveData().observe(getViewLifecycleOwner(), worksModel -> {
      if (!worksModel.mShown) {
        worksModel.mShown = true;
        Log.e("hhh", "ExploreFragment " + worksModel.mId + " shown");
      }
    });
  }
}
