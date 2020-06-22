package com.hhh.mvvm.recycler;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;

public abstract class RecyclerPagedListAdapter<MODEL> extends PagedListAdapter<MODEL, RecyclerViewHolder<MODEL>> {

  protected RecyclerPagedListAdapter(@NonNull DiffUtil.ItemCallback<MODEL> diffCallback) {
    super(diffCallback);
  }

  protected RecyclerPagedListAdapter(@NonNull AsyncDifferConfig<MODEL> config) {
    super(config);
  }

  @NonNull
  protected abstract RecyclerViewHolder<MODEL> onCreateItemViewHolder(@NonNull ViewGroup parent, int viewLayout);

  @LayoutRes
  protected abstract int getItemViewLayout(int position);

  @NonNull
  @Override
  public final RecyclerViewHolder<MODEL> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return onCreateItemViewHolder(parent, viewType);
  }

  @Override
  public final void onBindViewHolder(@NonNull RecyclerViewHolder<MODEL> holder, int position) {
    holder.bind(getItem(position));
  }

  @Override
  public final int getItemViewType(int position) {
    return getItemViewLayout(position);
  }
}
