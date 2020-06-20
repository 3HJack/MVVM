package com.hhh.onepiece.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hhh.mvvm.recycler.LoadingStatus;

public class HomeViewModel extends ViewModel {

  private final MutableLiveData<LoadingStatus> mExploreRefreshLiveData = new MutableLiveData<>();

  public LiveData<LoadingStatus> getExploreRefreshLiveData() {
    return mExploreRefreshLiveData;
  }

  public void setExploreRefreshLoadingStatus(LoadingStatus loadingStatus) {
    mExploreRefreshLiveData.postValue(loadingStatus);
  }
}
