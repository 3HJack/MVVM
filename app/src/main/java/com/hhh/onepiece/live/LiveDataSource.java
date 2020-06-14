package com.hhh.onepiece.live;

import com.hhh.mvvm.recycler.DataSourceSnapshot;
import com.hhh.mvvm.recycler.RecyclerDataSource;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.model.WorksModelGenerator;
import com.hhh.onepiece.response.LiveFeedResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LiveDataSource extends RecyclerDataSource<LiveFeedResponse, WorksModel, String> {

  public LiveDataSource(@NonNull String s,
                        @NonNull DataSourceSnapshot<?, WorksModel> dataSourceSnapshot) {
    super(s, dataSourceSnapshot);
  }

  @NonNull
  @Override
  protected Observable<LiveFeedResponse> onCreateInitialRequest(int loadSize,
                                                                @Nullable String pageKey) {
    return Observable.fromCallable(() -> {
      LiveFeedResponse response = new LiveFeedResponse();
      response.mCursor = String.valueOf(WorksModelGenerator.getID());
      response.mWorksList = WorksModelGenerator.createWorksModels(loadSize);
      return response;
    }).observeOn(AndroidSchedulers.mainThread());
  }
}
