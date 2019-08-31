package com.hhh.mvvm.recycler;

import android.view.View;

import com.hhh.mvvm.base.BaseActivity;
import com.hhh.mvvm.listener.OnImageShowListener;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerViewHolder<MODEL> extends RecyclerView.ViewHolder {

  protected MODEL mMODEL;

  public RecyclerViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @CallSuper
  public void bind(@NonNull MODEL model) {
    mMODEL = model;
  }

  @NonNull
  public MODEL getModel() {
    return mMODEL;
  }

  @Nullable
  public OnImageShowListener getImageShowListener() {
    return null;
  }

  @Nullable
  public final <T extends View> T findViewById(@IdRes int id) {
    return itemView.findViewById(id);
  }

  @NonNull
  public BaseActivity getActivity() {
    return (BaseActivity) itemView.getContext();
  }
}
