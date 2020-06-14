package com.hhh.onepiece.subscribe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hhh.mvvm.recycler.DiffCallbackProxy;
import com.hhh.mvvm.recycler.RecyclerFragment;
import com.hhh.mvvm.recycler.RecyclerPagedListAdapter;
import com.hhh.mvvm.recycler.RecyclerViewModel;
import com.hhh.onepiece.R;
import com.hhh.onepiece.model.WorksModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class SubscribeFragment extends RecyclerFragment<WorksModel, String> {

  @NonNull
  @Override
  protected RecyclerPagedListAdapter<WorksModel> onCreateAdapter() {
    return new SubscribePagedAdapter(new DiffCallbackProxy<>());
  }

  @NonNull
  @Override
  protected RecyclerViewModel<WorksModel, String> onCreateViewModel() {
    return new ViewModelProvider(this).get(SubscribeViewModel.class);
  }

  @Nullable
  @Override
  protected String onCreateParameter() {
    return "";
  }

  @Nullable
  @Override
  protected RecyclerView.ItemDecoration onCreateItemDecoration() {
    DividerItemDecoration decoration =
        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
    decoration.setDrawable(getResources().getDrawable(R.drawable.home_subscribe_divider_line));
    return decoration;
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
    mViewModel.getModelShowLiveData().observe(this, worksModel -> {
      if (!worksModel.mShown) {
        worksModel.mShown = true;
        Log.e("hhh", "SubscribeFragment " + worksModel.mId + " shown");
      }
    });
  }
}
