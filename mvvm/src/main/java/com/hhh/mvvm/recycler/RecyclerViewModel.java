package com.hhh.mvvm.recycler;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public abstract class RecyclerViewModel<MODEL, PARAMETER> extends ViewModel {

  protected final DataSourceSnapshot<?, MODEL> mDataSourceSnapshot;
  protected final MutableLiveData<MODEL> mModelShowLiveData;
  protected final MutableLiveData<ScrollStatus> mScrollStatusLiveData;
  protected final MutableLiveData<PARAMETER> mStartLoadLiveData;
  protected final MutableLiveData<RecyclerDataSource> mDataSourceLiveData;
  protected final LiveData<PagedList<MODEL>> mPagedListLiveData;
  protected final LiveData<LoadingStatus> mRefreshLiveData;
  protected final LiveData<LoadingStatus> mBeforeLiveData;
  protected final LiveData<LoadingStatus> mAfterLiveData;
  protected final LiveData<LoadingStatus> mInsertLiveData;
  protected final LiveData<LoadingStatus> mRemoveLiveData;
  protected final LiveData<LoadingStatus> mUpdateLiveData;
  protected final MediatorLiveData<LoadingStatus> mStateLiveData;

  public RecyclerViewModel() {
    mDataSourceSnapshot = new DataSourceSnapshot<>();
    mModelShowLiveData = new MutableLiveData<>();
    mScrollStatusLiveData = new MutableLiveData<>();
    mStartLoadLiveData = new MutableLiveData<>();
    mDataSourceLiveData = new MutableLiveData<>();
    mPagedListLiveData = Transformations.switchMap(mStartLoadLiveData,
        input -> new LivePagedListBuilder<>(new DataSource.Factory<String, MODEL>() {
          @Override
          public DataSource<String, MODEL> create() {
            RecyclerDataSource<?, MODEL, PARAMETER> dataSource =
                onCreateDataSource(input, mDataSourceSnapshot);
            mDataSourceLiveData.postValue(dataSource);
            return dataSource;
          }
        }, onCreatePagedListConfig())
            .setBoundaryCallback(onCreateBoundaryCallback())
            .build());
    mRefreshLiveData = Transformations.switchMap(mDataSourceLiveData,
        (Function<RecyclerDataSource, LiveData<LoadingStatus>>) input -> input.mInitialLiveData);
    mBeforeLiveData = Transformations.switchMap(mDataSourceLiveData,
        (Function<RecyclerDataSource, LiveData<LoadingStatus>>) input -> input.mBeforeLiveData);
    mAfterLiveData = Transformations.switchMap(mDataSourceLiveData,
        (Function<RecyclerDataSource, LiveData<LoadingStatus>>) input -> input.mAfterLiveData);
    mInsertLiveData = Transformations.switchMap(mDataSourceLiveData,
        (Function<RecyclerDataSource, LiveData<LoadingStatus>>) input -> input.mInsertLiveData);
    mRemoveLiveData = Transformations.switchMap(mDataSourceLiveData,
        (Function<RecyclerDataSource, LiveData<LoadingStatus>>) input -> input.mRemoveLiveData);
    mUpdateLiveData = Transformations.switchMap(mDataSourceLiveData,
        (Function<RecyclerDataSource, LiveData<LoadingStatus>>) input -> input.mUpdateLiveData);
    mStateLiveData = new MediatorLiveData<>();
    mStateLiveData.addSource(mRefreshLiveData,
        loadingStatus -> mStateLiveData.setValue(loadingStatus));
    mStateLiveData.addSource(mBeforeLiveData,
        loadingStatus -> mStateLiveData.setValue(loadingStatus));
    mStateLiveData.addSource(mAfterLiveData,
        loadingStatus -> mStateLiveData.setValue(loadingStatus));
    mStateLiveData.addSource(mInsertLiveData,
        loadingStatus -> mStateLiveData.setValue(loadingStatus));
    mStateLiveData.addSource(mRemoveLiveData,
        loadingStatus -> mStateLiveData.setValue(loadingStatus));
    mStateLiveData.addSource(mUpdateLiveData,
        loadingStatus -> mStateLiveData.setValue(loadingStatus));
  }

  @NonNull
  protected abstract RecyclerDataSource<?, MODEL, PARAMETER> onCreateDataSource(PARAMETER parameter,
                                                                                @NonNull DataSourceSnapshot<?, MODEL> dataSourceSnapshot);

  @NonNull
  public DataSourceSnapshot<?, MODEL> getDataSourceSnapshot() {
    return mDataSourceSnapshot;
  }

  @NonNull
  public LiveData<MODEL> getModelShowLiveData() {
    return mModelShowLiveData;
  }

  @MainThread
  public void setModelShow(@NonNull MODEL model) {
    mModelShowLiveData.setValue(model);
  }

  @NonNull
  public LiveData<ScrollStatus> getScrollStatusLiveData() {
    return mScrollStatusLiveData;
  }

  @MainThread
  public void setScrollStatus(@NonNull ScrollStatus scrollStatus) {
    mScrollStatusLiveData.setValue(scrollStatus);
  }

  @NonNull
  public LiveData<PARAMETER> getStartLoadLiveData() {
    return mStartLoadLiveData;
  }

  @NonNull
  public LiveData<PagedList<MODEL>> getPagedListLiveData() {
    return mPagedListLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getRefreshLiveData() {
    return mRefreshLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getBeforeLiveData() {
    return mBeforeLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getAfterLiveData() {
    return mAfterLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getInsertLiveData() {
    return mInsertLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getRemoveLiveData() {
    return mRemoveLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getUpdateLiveData() {
    return mUpdateLiveData;
  }

  @NonNull
  public LiveData<LoadingStatus> getLoadingStateLiveData() {
    return mStateLiveData;
  }

  public void loadData(@Nullable PARAMETER parameter) {
    if (parameter == null || parameter.equals(mStartLoadLiveData.getValue())) {
      return;
    }
    mStartLoadLiveData.postValue(parameter);
  }

  public void refresh() {
    RecyclerDataSource dataSource = mDataSourceLiveData.getValue();
    if (dataSource != null) {
      dataSource.refresh();
    }
  }

  public void retry() {
    RecyclerDataSource dataSource = mDataSourceLiveData.getValue();
    if (dataSource != null) {
      dataSource.retry();
    }
  }

  public void remove(int position) {
    RecyclerDataSource dataSource = mDataSourceLiveData.getValue();
    if (dataSource != null) {
      dataSource.remove(position);
    }
  }

  public void insert(int position, MODEL model) {
    RecyclerDataSource dataSource = mDataSourceLiveData.getValue();
    if (dataSource != null) {
      dataSource.insert(position, model);
    }
  }

  public void update(int position, MODEL model) {
    RecyclerDataSource dataSource = mDataSourceLiveData.getValue();
    if (dataSource != null) {
      dataSource.update(position, model);
    }
  }

  public void clear() {
    mDataSourceSnapshot.mModels.clear();
  }

  @NonNull
  protected PagedList.Config onCreatePagedListConfig() {
    return new PagedList.Config.Builder().setInitialLoadSizeHint(20).setPageSize(20)
        .setPrefetchDistance(8).build();
  }

  @Nullable
  protected PagedList.BoundaryCallback<MODEL> onCreateBoundaryCallback() {
    return null;
  }
}
