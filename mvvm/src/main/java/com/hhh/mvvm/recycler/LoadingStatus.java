package com.hhh.mvvm.recycler;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadingStatus implements Parcelable {

  public static final LoadingStatus STATE_RUNNING = new LoadingStatus(Status.RUNNING, null);
  public static final LoadingStatus STATE_SUCCESS = new LoadingStatus(Status.SUCCESS, null);
  public static final LoadingStatus STATE_EMPTY = new LoadingStatus(Status.EMPTY, null);
  public static final LoadingStatus STATE_NOMORE = new LoadingStatus(Status.NOMORE, null);
  public static final LoadingStatus STATE_INSERT = new LoadingStatus(Status.INSERT, null);
  public static final LoadingStatus STATE_REMOVE = new LoadingStatus(Status.REMOVE, null);
  public static final LoadingStatus STATE_UPDATE = new LoadingStatus(Status.UPDATE, null);

  public static final Creator<LoadingStatus> CREATOR = new Creator<LoadingStatus>() {
    @Override
    public LoadingStatus createFromParcel(Parcel source) {
      return new LoadingStatus(source);
    }

    @Override
    public LoadingStatus[] newArray(int size) {
      return new LoadingStatus[size];
    }
  };

  public final Status mStatus;
  public final Throwable mThrowable;

  public LoadingStatus(@NonNull Status status, @Nullable Throwable throwable) {
    mStatus = status;
    mThrowable = throwable;
  }

  protected LoadingStatus(Parcel in) {
    int tmpMStatus = in.readInt();
    this.mStatus = tmpMStatus == -1 ? null : Status.values()[tmpMStatus];
    this.mThrowable = (Throwable) in.readSerializable();
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    if (!(obj instanceof LoadingStatus)) {
      return false;
    }
    LoadingStatus loadingStatus = (LoadingStatus) obj;
    return mStatus == loadingStatus.mStatus
      && TextUtils.equals(getMessage(mThrowable), getMessage(loadingStatus.mThrowable));
  }

  private String getMessage(Throwable throwable) {
    return throwable == null ? null : throwable.getMessage();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
    dest.writeSerializable(this.mThrowable);
  }

  public enum Status {
    RUNNING,
    SUCCESS,
    EMPTY,
    NOMORE,
    FAILED,
    INSERT,
    REMOVE,
    UPDATE
  }
}
