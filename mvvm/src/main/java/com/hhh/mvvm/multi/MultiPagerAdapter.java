package com.hhh.mvvm.multi;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.base.BaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 不支持动态添加和移除fragment
 */
public abstract class MultiPagerAdapter extends FragmentPagerAdapter {

  private final List<BaseFragment> mFragmentList;
  private final FragmentManager mFragmentManager;
  private final int mViewId;

  public MultiPagerAdapter(FragmentManager fm, int viewId, int count) {
    super(fm);
    mFragmentManager = fm;
    mViewId = viewId;
    mFragmentList = new ArrayList<>(count);
    for (int i = 0; i < count; ++i) {
      mFragmentList.add(null);
    }
  }

  @NonNull
  protected abstract BaseFragment onCreateFragment(int position);

  @Override
  public BaseFragment getItem(int position) {
    BaseFragment fragment = mFragmentList.get(position);
    if (fragment != null) {
      return fragment;
    }
    fragment = (BaseFragment) mFragmentManager
      .findFragmentByTag(BaseUtils.makeFragmentName(mViewId, getItemId(position)));
    if (fragment == null) {
      fragment = onCreateFragment(position);
    }
    mFragmentList.set(position, fragment);
    return fragment;
  }

  @Override
  public int getCount() {
    return mFragmentList.size();
  }
}
