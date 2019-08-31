package com.hhh.mvvm.recycler;

import com.hhh.mvvm.listener.DiffCallback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DiffCallbackProxy<MODEL extends DiffCallback> extends DiffUtil.ItemCallback<MODEL> {

  @Override
  public boolean areItemsTheSame(@NonNull MODEL oldItem, @NonNull MODEL newItem) {
    return oldItem.areItemsTheSame(newItem);
  }

  @Override
  public boolean areContentsTheSame(@NonNull MODEL oldItem, @NonNull MODEL newItem) {
    return oldItem.areContentsTheSame(newItem);
  }
}
