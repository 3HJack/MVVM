package com.hhh.mvvm.multi;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hhh.mvvm.R;
import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.listener.OnViewScrollListener;
import com.hhh.mvvm.view.RefreshFooterView;
import com.hhh.mvvm.view.RefreshHeadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public abstract class MultiFragment extends BaseFragment {

  protected ViewPager mViewPager;
  protected TabLayout mTabLayout;
  protected ViewGroup mHeadContainerView;
  protected SmartRefreshLayout mRefreshLayout;
  protected MultiPagerAdapter mPagerAdapter;
  protected BaseFragment mHeadFragment;
  protected MultiViewModel mViewModel;

  protected abstract MultiPagerAdapter onCreatePagerAdapter();

  @Nullable
  protected BaseFragment onCreateHeadFragment() {
    return null;
  }

  @NonNull
  protected MultiViewModel onCreateViewModel() {
    return new ViewModelProvider(this).get(MultiViewModel.class);
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

  protected boolean enableRefresh() {
    return false;
  }

  protected boolean enableLoadMore() {
    return false;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewModel = onCreateViewModel();
    initViewPager();
    initTabLayout();
    initHeadView();
    initRefreshView();
    monitorScrollStatus();
  }

  @Nullable
  public BaseFragment getCurrentFragment() {
    if (mViewPager != null) {
      return mPagerAdapter.getItem(mViewPager.getCurrentItem());
    }
    return null;
  }

  public void gotoSpecialFragment(int position) {
    if (mViewPager != null && position >= 0 && position <= mPagerAdapter.getCount()) {
      mViewPager.setCurrentItem(position);
    }
  }

  private void initViewPager() {
    mViewPager = findViewById(R.id.view_pager);
    mPagerAdapter = onCreatePagerAdapter();
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.setOffscreenPageLimit(2);
  }

  private void initTabLayout() {
    mTabLayout = findViewById(R.id.tab_layout);
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        mPagerAdapter.getItem(mTabLayout.getSelectedTabPosition()).onPageSelected();
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
        mPagerAdapter.getItem(mTabLayout.getSelectedTabPosition()).onPageUnselected();
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {
        mPagerAdapter.getItem(mTabLayout.getSelectedTabPosition()).onPageReselected();
      }
    });
  }

  private void initHeadView() {
    mHeadContainerView = findViewById(R.id.head_container);
    if (mHeadContainerView == null) {
      return;
    }
    FragmentManager fragmentManager = getChildFragmentManager();
    mHeadFragment = (BaseFragment) fragmentManager.findFragmentById(R.id.head_container);
    if (mHeadFragment == null) {
      mHeadFragment = onCreateHeadFragment();
      if (mHeadFragment != null) {
        fragmentManager.beginTransaction()
            .replace(R.id.head_container, mHeadFragment, mHeadFragment.getClass().getName())
            .commit();
      }
    }
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
      mRefreshLayout.setOnRefreshListener(refreshLayout -> {
        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof OnRefreshListener) {
          ((OnRefreshListener) currentFragment).onRefresh(mRefreshLayout);
        }
      });
    }
    boolean enableLoadMore = enableLoadMore();
    mRefreshLayout.setEnableLoadMore(enableLoadMore);
    if (enableLoadMore) {
      mRefreshLayout.setRefreshFooter(onCreateRefreshFooterView());
      mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof OnLoadMoreListener) {
          ((OnLoadMoreListener) currentFragment).onLoadMore(mRefreshLayout);
        }
      });
    }
  }

  private void monitorScrollStatus() {
    OnViewScrollListener viewScrollListener = getViewScrollListener();
    if (viewScrollListener != null) {
      viewScrollListener
          .setViewScrollCallback(scrollStatus -> mViewModel.postScrollStatus(scrollStatus));
    }
  }
}
