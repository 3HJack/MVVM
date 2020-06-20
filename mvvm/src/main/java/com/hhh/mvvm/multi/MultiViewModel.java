package com.hhh.mvvm.multi;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hhh.mvvm.recycler.ScrollStatus;

public class MultiViewModel extends ViewModel {

  protected final MutableLiveData<ScrollStatus> mScrollStatusLiveData = new MutableLiveData<>();

  @NonNull
  public LiveData<ScrollStatus> getScrollStatusLiveData() {
    return mScrollStatusLiveData;
  }

  public void postScrollStatus(@NonNull ScrollStatus scrollStatus) {
    mScrollStatusLiveData.postValue(scrollStatus);
  }
}
