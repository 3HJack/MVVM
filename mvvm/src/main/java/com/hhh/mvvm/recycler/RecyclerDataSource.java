package com.hhh.mvvm.recycler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.hhh.mvvm.base.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class RecyclerDataSource<PAGE extends RecyclerResponse<MODEL>, MODEL, PARAMETER> extends PageKeyedDataSource<String, MODEL> {

  private static Handler sAsyncHandler;

  protected final MutableLiveData<LoadingStatus> mInitialLiveData = new MutableLiveData<>();
  protected final MutableLiveData<LoadingStatus> mBeforeLiveData = new MutableLiveData<>();
  protected final MutableLiveData<LoadingStatus> mAfterLiveData = new MutableLiveData<>();
  protected final MutableLiveData<LoadingStatus> mInsertLiveData = new MutableLiveData<>();
  protected final MutableLiveData<LoadingStatus> mRemoveLiveData = new MutableLiveData<>();
  protected final MutableLiveData<LoadingStatus> mUpdateLiveData = new MutableLiveData<>();

  protected final PARAMETER mParameter;
  protected final DataSourceSnapshot<PAGE, MODEL> mDataSourceSnapshot;
  private Runnable mRetry;

  public RecyclerDataSource(@NonNull PARAMETER parameter,
    @NonNull DataSourceSnapshot<?, MODEL> dataSourceSnapshot) {
    mParameter = parameter;
    mDataSourceSnapshot = (DataSourceSnapshot<PAGE, MODEL>) dataSourceSnapshot;
  }

  private static Handler getsAsyncHandler() {
    if (sAsyncHandler == null) {
      HandlerThread handlerThread = new HandlerThread("mvvm");
      handlerThread.start();
      sAsyncHandler = new Handler(handlerThread.getLooper());
    }
    return sAsyncHandler;
  }

  @NonNull
  protected abstract Observable<PAGE> onCreateInitialRequest(int loadSize,
    @Nullable String pageKey);

  @Nullable
  protected Observable<PAGE> onCreateBeforeRequest(int loadSize, @Nullable String pageKey) {
    return null;
  }

  @NonNull
  protected Observable<PAGE> onCreateAfterRequest(int loadSize, @Nullable String pageKey) {
    return onCreateInitialRequest(loadSize, pageKey);
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadInitial(@NonNull LoadInitialParams<String> params,
    @NonNull LoadInitialCallback<String, MODEL> callback) {
    if (mDataSourceSnapshot.mInsertingItem || mDataSourceSnapshot.mRemovingItem || mDataSourceSnapshot.mUpdatingItem) {
      callback.onResult(mDataSourceSnapshot.getModels(), getPreviousPageKey(), getNextPageKey());
      if (mDataSourceSnapshot.mInsertingItem) {
        mDataSourceSnapshot.mInsertingItem = false;
        mInsertLiveData.postValue(LoadingStatus.STATE_INSERT);
      }
      if (mDataSourceSnapshot.mRemovingItem) {
        mDataSourceSnapshot.mRemovingItem = false;
        mRemoveLiveData.postValue(LoadingStatus.STATE_REMOVE);
      }
      if (mDataSourceSnapshot.mUpdatingItem) {
        mDataSourceSnapshot.mUpdatingItem = false;
        mUpdateLiveData.postValue(LoadingStatus.STATE_UPDATE);
      }
      return;
    }
    mInitialLiveData.postValue(LoadingStatus.STATE_RUNNING);
    onCreateInitialRequest(params.requestedLoadSize, null).subscribe(page -> {
      mDataSourceSnapshot.mPage = page;
      List<MODEL> modelList = page.getItems();
      if (!BaseUtils.isEmpty(modelList)) {
        mDataSourceSnapshot.mModels.clear();
        mDataSourceSnapshot.mModels.addAll(modelList);
        callback.onResult(new ArrayList<>(modelList), getPreviousPageKey(), getNextPageKey());
      } else if (!mDataSourceSnapshot.mModels.isEmpty()) {
        callback.onResult(mDataSourceSnapshot.getModels(), getPreviousPageKey(), getNextPageKey());
      }
      onLoadInitialSuccess();
    }, throwable -> {
      mRetry = () -> sAsyncHandler.post(() -> loadInitial(params, callback));
      if (!mDataSourceSnapshot.mModels.isEmpty()) {
        callback.onResult(mDataSourceSnapshot.getModels(), getPreviousPageKey(), getNextPageKey());
      }
      onLoadInitialFail(throwable);
    });
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadBefore(@NonNull LoadParams<String> params,
    @NonNull LoadCallback<String, MODEL> callback) {
    Observable<PAGE> request = onCreateBeforeRequest(params.requestedLoadSize,
      getPreviousPageKey());
    if (request != null) {
      mBeforeLiveData.postValue(LoadingStatus.STATE_RUNNING);
      request.subscribe(page -> {
        mDataSourceSnapshot.mPage = page;
        List<MODEL> modelList = page.getItems();
        if (!BaseUtils.isEmpty(modelList)) {
          mDataSourceSnapshot.mModels.addAll(0, modelList);
          callback.onResult(new ArrayList<>(modelList), getPreviousPageKey());
        }
        onLoadBeforeSuccess();
      }, throwable -> {
        mRetry = () -> sAsyncHandler.post(() -> loadBefore(params, callback));
        onLoadBeforeFail(throwable);
      });
    }
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadAfter(@NonNull LoadParams<String> params,
    @NonNull LoadCallback<String, MODEL> callback) {
    mAfterLiveData.postValue(LoadingStatus.STATE_RUNNING);
    onCreateAfterRequest(params.requestedLoadSize, getNextPageKey()).subscribe(page -> {
      mDataSourceSnapshot.mPage = page;
      List<MODEL> modelList = page.getItems();
      if (!BaseUtils.isEmpty(modelList)) {
        mDataSourceSnapshot.mModels.addAll(modelList);
        callback.onResult(new ArrayList<>(modelList), getNextPageKey());
      }
      onLoadAfterSuccess();
    }, throwable -> {
      mRetry = () -> sAsyncHandler.post(() -> loadAfter(params, callback));
      onLoadAfterFail(throwable);
    });
  }

  void refresh() {
    invalidate();
  }

  void retry() {
    if (mRetry != null) {
      mRetry.run();
      mRetry = null;
    }
  }

  void remove(int position) {
    if (position >= 0 && position < mDataSourceSnapshot.mModels.size()) {
      mDataSourceSnapshot.mModels.remove(position);
      mDataSourceSnapshot.mRemovingItem = true;
      invalidate();
    }
  }

  void insert(int position, MODEL model) {
    if (position >= 0 && position <= mDataSourceSnapshot.mModels.size()) {
      mDataSourceSnapshot.mModels.add(position, model);
      mDataSourceSnapshot.mInsertingItem = true;
      invalidate();
    }
  }

  void update(int position, MODEL model) {
    if (position >= 0 && position < mDataSourceSnapshot.mModels.size()) {
      mDataSourceSnapshot.mModels.set(position, model);
      mDataSourceSnapshot.mUpdatingItem = true;
      invalidate();
    }
  }

  @Nullable
  public String getNextPageKey() {
    return mDataSourceSnapshot.mPage == null ? null : mDataSourceSnapshot.mPage.getNextPageKey();
  }

  @Nullable
  public String getPreviousPageKey() {
    return mDataSourceSnapshot.mPage == null ? null :
      mDataSourceSnapshot.mPage.getPreviousPageKey();
  }

  protected void onLoadInitialSuccess() {
    if (mDataSourceSnapshot.mModels.isEmpty()) {
      mInitialLiveData.postValue(LoadingStatus.STATE_EMPTY);
    } else if (!mDataSourceSnapshot.mPage.hasMore()) {
      mInitialLiveData.postValue(LoadingStatus.STATE_NOMORE);
    } else {
      mInitialLiveData.postValue(LoadingStatus.STATE_SUCCESS);
    }
  }

  protected void onLoadInitialFail(@NonNull Throwable throwable) {
    mInitialLiveData.postValue(new LoadingStatus(LoadingStatus.Status.FAILED, throwable));
  }

  protected void onLoadBeforeSuccess() {
    if (mDataSourceSnapshot.mModels.isEmpty()) {
      mBeforeLiveData.postValue(LoadingStatus.STATE_EMPTY);
    } else if (!mDataSourceSnapshot.mPage.hasPreviousMore()) {
      mBeforeLiveData.postValue(LoadingStatus.STATE_NOMORE);
    } else {
      mBeforeLiveData.postValue(LoadingStatus.STATE_SUCCESS);
    }
  }

  protected void onLoadBeforeFail(@NonNull Throwable throwable) {
    mBeforeLiveData.postValue(new LoadingStatus(LoadingStatus.Status.FAILED, throwable));
  }

  protected void onLoadAfterSuccess() {
    if (mDataSourceSnapshot.mModels.isEmpty()) {
      mAfterLiveData.postValue(LoadingStatus.STATE_EMPTY);
    } else if (!mDataSourceSnapshot.mPage.hasMore()) {
      mAfterLiveData.postValue(LoadingStatus.STATE_NOMORE);
    } else {
      mAfterLiveData.postValue(LoadingStatus.STATE_SUCCESS);
    }
  }

  protected void onLoadAfterFail(@NonNull Throwable throwable) {
    mAfterLiveData.postValue(new LoadingStatus(LoadingStatus.Status.FAILED, throwable));
  }
}
