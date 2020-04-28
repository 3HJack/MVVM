package com.hhh.onepiece.explore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hhh.mvvm.recycler.DataSourceSnapshot;
import com.hhh.mvvm.recycler.RecyclerDataSource;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.model.WorksModelGenerator;
import com.hhh.onepiece.response.ExploreFeedResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ExploreDataSource extends RecyclerDataSource<ExploreFeedResponse, WorksModel, String> {

  public ExploreDataSource(@NonNull String s,
      @NonNull DataSourceSnapshot<?, WorksModel> dataSourceSnapshot) {
    super(s, dataSourceSnapshot);
  }

  @NonNull
  @Override
  protected Observable<ExploreFeedResponse> onCreateInitialRequest(int loadSize,
      @Nullable String pageKey) {
    return Observable.fromCallable(() -> {
      ExploreFeedResponse response = new ExploreFeedResponse();
      response.mCursor = String.valueOf(WorksModelGenerator.getID());
      response.mWorksList = WorksModelGenerator.createWorksModels(loadSize);
      return response;
    }).observeOn(AndroidSchedulers.mainThread());
  }
}
