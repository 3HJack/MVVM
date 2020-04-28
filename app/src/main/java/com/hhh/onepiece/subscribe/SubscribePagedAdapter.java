package com.hhh.onepiece.subscribe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hhh.mvvm.recycler.RecyclerPagedListAdapter;
import com.hhh.mvvm.recycler.RecyclerViewHolder;
import com.hhh.onepiece.R;
import com.hhh.onepiece.model.WorksModel;

public class SubscribePagedAdapter extends RecyclerPagedListAdapter<WorksModel> {

  public SubscribePagedAdapter(@NonNull DiffUtil.ItemCallback<WorksModel> diffCallback) {
    super(diffCallback);
  }

  @NonNull
  @Override
  protected RecyclerViewHolder<WorksModel> onCreateItemViewHolder(@NonNull ViewGroup parent,
      int viewLayout) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewLayout, parent, false);
    return new SubscribeViewHolder(view);
  }

  @Override
  protected int getItemViewLayout(int position) {
    return R.layout.home_subscribe_item_layout;
  }
}
