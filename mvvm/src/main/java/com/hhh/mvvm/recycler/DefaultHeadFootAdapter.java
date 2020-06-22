package com.hhh.mvvm.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hhh.mvvm.R;
import com.hhh.mvvm.base.BaseFragment;

import java.lang.ref.WeakReference;

public class DefaultHeadFootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private WeakReference<RecyclerFragment<?, ?>> mRecyclerFragment;

  public DefaultHeadFootAdapter(RecyclerFragment<?, ?> recyclerFragment) {
    mRecyclerFragment = new WeakReference<>(recyclerFragment);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false);
    Fragment fragment = onCreateFragment();
    if (fragment != null && mRecyclerFragment.get() != null) {
      mRecyclerFragment.get().getChildFragmentManager().beginTransaction().replace(R.id.head_container, fragment, fragment.getClass().getName()).commit();
    }
    return new RecyclerView.ViewHolder(view) {
    };
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
          return gridLayoutManager.getSpanCount();
        }
      });
    }
  }

  @Override
  public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
    if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
      ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
    }
  }

  @Override
  public int getItemCount() {
    return 1;
  }

  @LayoutRes
  protected int getLayout() {
    return R.layout.mvvm_head_foot_layout;
  }

  protected BaseFragment onCreateFragment() {
    return null;
  }
}
