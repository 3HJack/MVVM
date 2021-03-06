package com.hhh.onepiece.subscribe;

import androidx.annotation.NonNull;

import com.hhh.mvvm.recycler.DataSourceSnapshot;
import com.hhh.mvvm.recycler.RecyclerDataSource;
import com.hhh.mvvm.recycler.RecyclerViewModel;
import com.hhh.onepiece.model.WorksModel;

public class SubscribeViewModel extends RecyclerViewModel<WorksModel, String> {

  @NonNull
  @Override
  protected RecyclerDataSource<?, WorksModel, String> onCreateDataSource(String s,
      @NonNull DataSourceSnapshot<?, WorksModel> dataSourceSnapshot) {
    return new SubscribeDataSource(s, dataSourceSnapshot);
  }
}
