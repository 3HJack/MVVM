package com.hhh.onepiece.subscribe;

import com.hhh.mvvm.recycler.DataSourceSnapshot;
import com.hhh.mvvm.recycler.RecyclerDataSource;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.model.WorksModelGenerator;
import com.hhh.onepiece.response.HomeResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SubscribeDataSource extends RecyclerDataSource<HomeResponse, WorksModel, String> {

  public SubscribeDataSource(@NonNull String s,
      @NonNull DataSourceSnapshot<?, WorksModel> dataSourceSnapshot) {
    super(s, dataSourceSnapshot);
  }

  @NonNull
  @Override
  protected Observable<HomeResponse> onCreateInitialRequest(int loadSize,
      @Nullable String pageKey) {
    return Observable.fromCallable(() -> {
      HomeResponse response = new HomeResponse();
      response.mCursor = String.valueOf(WorksModelGenerator.getID());
      response.mWorksList = WorksModelGenerator.createWorksModels(loadSize);
      return response;
    }).observeOn(AndroidSchedulers.mainThread());
  }
}
