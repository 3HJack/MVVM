package com.hhh.onepiece.live;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.recycler.DefaultHeadFootAdapter;
import com.hhh.mvvm.recycler.DiffCallbackProxy;
import com.hhh.mvvm.recycler.RecyclerFragment;
import com.hhh.mvvm.recycler.RecyclerPagedListAdapter;
import com.hhh.mvvm.recycler.RecyclerViewModel;
import com.hhh.onepiece.model.WorksModel;

public class LiveFragment extends RecyclerFragment<WorksModel, String> {

  @NonNull
  @Override
  protected RecyclerPagedListAdapter<WorksModel> onCreateAdapter() {
    return new LivePagedAdapter(new DiffCallbackProxy<>());
  }

  @NonNull
  @Override
  protected RecyclerViewModel<WorksModel, String> onCreateViewModel() {
    return new ViewModelProvider(this).get(LiveViewModel.class);
  }

  @Nullable
  @Override
  protected String onCreateParameter() {
    return "";
  }

  @Override
  protected boolean hasFixedSize() {
    return true;
  }

  @Override
  protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> onCreateHeadAdapter() {
    return new DefaultHeadFootAdapter(this) {
      @Override
      protected BaseFragment onCreateFragment() {
        return new LiveHeadFragment();
      }
    };
  }
}
