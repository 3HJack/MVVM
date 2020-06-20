package com.hhh.mvvm.recycler;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hhh.mvvm.R;
import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.listener.OnImageShowListener;
import com.hhh.mvvm.listener.OnViewScrollListener;
import com.hhh.mvvm.multi.MultiFragment;
import com.hhh.mvvm.view.RefreshFooterView;
import com.hhh.mvvm.view.RefreshHeadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerFragment<MODEL, PARAMETER> extends BaseFragment implements OnRefreshLoadMoreListener {

  protected static final int MODEL_SHOW_TYPE_NONE = 0;
  protected static final int MODEL_SHOW_TYPE_ITEM = 1;
  protected static final int MODEL_SHOW_TYPE_COVER = 2;

  protected BaseFragment mEmptyFragment;
  protected ViewGroup mEmptyContainerView;
  protected SmartRefreshLayout mRefreshLayout;
  protected RecyclerView mRecyclerView;
  protected MergeAdapter mMergeAdapter;
  protected RecyclerPagedListAdapter<MODEL> mAdapter;
  protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> mHeadAdapter;
  protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> mFootAdapter;
  protected RecyclerViewModel<MODEL, PARAMETER> mViewModel;
  protected int mShowType;
  protected boolean mRefreshing;

  @LayoutRes
  @Override
  protected int getLayoutResId() {
    return R.layout.mvvm_recycler_layout;
  }

  @NonNull
  protected abstract RecyclerPagedListAdapter<MODEL> onCreateAdapter();

  protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> onCreateHeadAdapter() {
    return null;
  }

  protected RecyclerView.Adapter<? extends RecyclerView.ViewHolder> onCreateFootAdapter() {
    return null;
  }

  @NonNull
  protected abstract RecyclerViewModel<MODEL, PARAMETER> onCreateViewModel();

  @Nullable
  protected abstract PARAMETER onCreateParameter();

  protected abstract boolean hasFixedSize();

  @NonNull
  protected RecyclerView.LayoutManager onCreateLayoutManager() {
    return new LinearLayoutManager(getContext());
  }

  @Nullable
  protected RecyclerView.ItemDecoration onCreateItemDecoration() {
    return null;
  }

  @Nullable
  protected BaseFragment onCreateEmptyFragment() {
    return new DefaultEmptyFragment();
  }

  @NonNull
  protected RefreshHeader onCreateRefreshHeaderView() {
    return new RefreshHeadView(getContext());
  }

  @NonNull
  protected RefreshFooter onCreateRefreshFooterView() {
    return new RefreshFooterView(getContext());
  }

  @Nullable
  protected OnViewScrollListener getViewScrollListener() {
    return null;
  }

  protected int getModelShowType() {
    return MODEL_SHOW_TYPE_NONE;
  }

  protected boolean enableRefresh() {
    return true;
  }

  protected boolean enableLoadMore() {
    return true;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initRecycleView();
    initRefreshView();
    initEmptyView();
    monitorScrollStatus();
    monitorModelShowStatus();
    if (isCurrentFragment()) {
      mViewModel.loadData(onCreateParameter());
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    checkModelShowStatus();
  }

  @Override
  public void onPageSelected() {
    if (isPageEmpty()) {
      mViewModel.loadData(onCreateParameter());
    }
  }

  @Override
  public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    mViewModel.retry();
  }

  @Override
  public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    mViewModel.refresh();
  }

  @NonNull
  public RecyclerViewModel<MODEL, PARAMETER> getViewModel() {
    return mViewModel;
  }

  public boolean isCurrentFragment() {
    Fragment fragment = getParentFragment();
    if (fragment instanceof MultiFragment) {
      return ((MultiFragment) fragment).getCurrentFragment() == this;
    }
    return true;
  }

  public boolean isPageEmpty() {
    return mAdapter != null && mAdapter.getItemCount() == 0;
  }

  public boolean removeAdapter() {
    if (mHeadAdapter != null) {
      return mMergeAdapter.removeAdapter(mHeadAdapter);
    }
    return false;
  }

  public boolean removeFootAdapter() {
    if (mFootAdapter != null) {
      return mMergeAdapter.removeAdapter(mFootAdapter);
    }
    return false;
  }

  private void initRecycleView() {
    mRecyclerView = findViewById(R.id.recycler_view);
    mRecyclerView.setLayoutManager(onCreateLayoutManager());
    mRecyclerView.setHasFixedSize(hasFixedSize());
    RecyclerView.ItemDecoration itemDecoration = onCreateItemDecoration();
    if (itemDecoration != null) {
      mRecyclerView.addItemDecoration(itemDecoration);
    }
    List<RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> adapterList = new ArrayList<>();
    mHeadAdapter = onCreateHeadAdapter();
    if (mHeadAdapter != null) {
      adapterList.add(mHeadAdapter);
    }
    adapterList.add(mAdapter = onCreateAdapter());
    mFootAdapter = onCreateFootAdapter();
    if (mFootAdapter != null) {
      adapterList.add(mFootAdapter);
    }
    mMergeAdapter = new MergeAdapter(adapterList);
    mRecyclerView.setAdapter(mMergeAdapter);
    mViewModel = onCreateViewModel();
    mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(),
      models -> mAdapter.submitList(models));
  }

  private void initRefreshView() {
    mRefreshLayout = findViewById(R.id.refresh_layout);
    if (mRefreshLayout == null) {
      return;
    }
    boolean enableRefresh = enableRefresh();
    mRefreshLayout.setEnableRefresh(enableRefresh);
    if (enableRefresh) {
      mRefreshLayout.setRefreshHeader(onCreateRefreshHeaderView());
      mRefreshLayout.setOnRefreshListener(this);
      mViewModel.getRefreshLiveData().observe(getViewLifecycleOwner(), loadingState -> {
        if (loadingState.mStatus != LoadingStatus.Status.RUNNING && mRefreshLayout.getState() == RefreshState.Refreshing) {
          if (loadingState.mStatus == LoadingStatus.Status.NOMORE) {
            mRefreshLayout.finishRefreshWithNoMoreData();
          } else {
            mRefreshLayout.finishRefresh();
          }
        }
      });
    }
    boolean enableLoadMore = enableLoadMore();
    mRefreshLayout.setEnableLoadMore(enableLoadMore);
    if (enableLoadMore) {
      mRefreshLayout.setRefreshFooter(onCreateRefreshFooterView());
      mRefreshLayout.setOnLoadMoreListener(this);
      mViewModel.getAfterLiveData().observe(getViewLifecycleOwner(), loadingState -> {
        if (loadingState.mStatus != LoadingStatus.Status.RUNNING && mRefreshLayout.getState() == RefreshState.Loading) {
          if (loadingState.mStatus == LoadingStatus.Status.NOMORE) {
            mRefreshLayout.finishLoadMoreWithNoMoreData();
          } else {
            mRefreshLayout.finishLoadMore();
          }
        }
      });
    }
  }

  private void initEmptyView() {
    mEmptyContainerView = findViewById(R.id.empty_container);
    if (mEmptyContainerView == null) {
      return;
    }
    mEmptyFragment =
      (BaseFragment) getChildFragmentManager().findFragmentById(R.id.empty_container);
    mViewModel.getLoadingStateLiveData().observe(getViewLifecycleOwner(), loadingState -> {
      if (loadingState.mStatus == LoadingStatus.Status.RUNNING || loadingState.mStatus == LoadingStatus.Status.UPDATE) {
        return;
      }
      if (loadingState.mStatus == LoadingStatus.Status.SUCCESS || loadingState.mStatus == LoadingStatus.Status.NOMORE || loadingState.mStatus == LoadingStatus.Status.INSERT) {
        hideEmptyView();
      } else if (loadingState.mStatus == LoadingStatus.Status.EMPTY || (loadingState.mStatus == LoadingStatus.Status.FAILED && isPageEmpty()) || (loadingState.mStatus == LoadingStatus.Status.REMOVE && isPageEmpty())) {
        showEmptyView();
      }
    });
  }

  private void showEmptyView() {
    if (mEmptyFragment == null) {
      mEmptyFragment = onCreateEmptyFragment();
    }
    if (mEmptyFragment == null) {
      return;
    }
    if (!mEmptyFragment.isAdded()) {
      getChildFragmentManager().beginTransaction().replace(R.id.empty_container, mEmptyFragment,
        mEmptyFragment.getClass().getName()).commit();
      mEmptyContainerView.setVisibility(View.VISIBLE);
    }
  }

  private void hideEmptyView() {
    if (mEmptyFragment != null && mEmptyFragment.isAdded()) {
      getChildFragmentManager().beginTransaction().remove(mEmptyFragment).commit();
      mEmptyContainerView.setVisibility(View.GONE);
    }
  }

  private void monitorScrollStatus() {
    OnViewScrollListener viewScrollListener = getViewScrollListener();
    if (viewScrollListener != null) {
      viewScrollListener.setViewScrollCallback(scrollStatus -> mViewModel.setScrollStatus(scrollStatus));
    }
  }

  private void monitorModelShowStatus() {
    mViewModel.getRefreshLiveData().observe(getViewLifecycleOwner(), loadingStatus -> {
      if (loadingStatus.mStatus == LoadingStatus.Status.RUNNING) {
        mRefreshing = true;
        checkModelShowStatus();
      } else {
        mRefreshing = false;
      }
    });
    mShowType = getModelShowType();
    if (mShowType == MODEL_SHOW_TYPE_NONE) {
      return;
    }
    mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
      @Override
      public void onChildViewAttachedToWindow(@NonNull View view) {
      }

      @Override
      public void onChildViewDetachedFromWindow(@NonNull View view) {
        if (!mRefreshing) {
          setModelShowEvent(view);
        }
      }
    });
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          checkModelShowStatus();
        }
      }
    });
  }

  private void checkModelShowStatus() {
    if (mShowType == MODEL_SHOW_TYPE_NONE) {
      return;
    }
    Rect rect = new Rect();
    for (int i = 0, count = mRecyclerView.getChildCount(); i < count; ++i) {
      View view = mRecyclerView.getChildAt(i);
      if (view.getGlobalVisibleRect(rect)) {
        setModelShowEvent(view);
      }
    }
  }

  private void setModelShowEvent(View view) {
    RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
    if (!(viewHolder instanceof RecyclerViewHolder)) {
      return;
    }
    RecyclerViewHolder<MODEL> recyclerViewHolder = (RecyclerViewHolder<MODEL>) viewHolder;
    MODEL model = recyclerViewHolder.getModel();
    if (mShowType == MODEL_SHOW_TYPE_ITEM) {
      mViewModel.setModelShow(model);
    } else {
      OnImageShowListener imageShowListener = recyclerViewHolder.getImageShowListener();
      if (imageShowListener != null && imageShowListener.hadShown()) {
        mViewModel.setModelShow(model);
      }
    }
  }
}
