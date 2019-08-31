package com.hhh.mvvm.listener;

import androidx.annotation.NonNull;

public interface DiffCallback<MODEL> {

  boolean areItemsTheSame(@NonNull MODEL newItem);

  boolean areContentsTheSame(@NonNull MODEL newItem);
}
