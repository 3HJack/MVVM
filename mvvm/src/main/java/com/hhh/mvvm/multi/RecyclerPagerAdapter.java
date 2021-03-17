package com.hhh.mvvm.multi;

import java.util.ArrayDeque;
import java.util.Deque;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

/**
 * 不会真正移除 Fragment，会对 Fragment 重复利用，在内存和性能方面都优化到了极致，建议笨重的页面使用，
 * 但需要开发者自己控制相关 Fragment 的生命周期。
 */
public abstract class RecyclerPagerAdapter<T> extends PagerAdapter {

    private static final String TAG = "hhhh";

    private final int mCount;
    private final FragmentManager mFragmentManager;
    private final Deque<Fragment> mCacheFragments = new ArrayDeque<>();

    private Fragment mCurrentFragment;
    private FragmentTransaction mCurTransaction;

    public RecyclerPagerAdapter(int count, @NonNull FragmentManager fragmentManager) {
        mCount = count;
        mFragmentManager = fragmentManager;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = getFragment(position);
        if (fragment instanceof DataBind) {
            ((DataBind) fragment).onDataBind(getModel(position));
        }
        Log.i(TAG, "instantiateItem: " + position + " " + fragment.toString());
        if (!fragment.isAdded()) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            mCurTransaction.add(container.getId(), fragment);
        }
        if (fragment != mCurrentFragment) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.i(TAG, "destroyItem: " + position + " " + object.toString());
        mCacheFragments.offerLast((Fragment) object);
        if (object == mCurrentFragment) {
            mCurrentFragment = null;
        }
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentFragment) {
            Log.i(TAG, "setPrimaryItem: " + position + " " + fragment.toString());
            if (mCurrentFragment != null) {
                mCurrentFragment.setMenuVisibility(false);
                mCurrentFragment.setUserVisibleHint(false);
            }
            fragment.setMenuVisibility(true);
            fragment.setUserVisibleHint(true);
            mCurrentFragment = fragment;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (mCurTransaction != null) {
            try {
                mCurTransaction.commitNowAllowingStateLoss();
            } catch (IllegalStateException e) {
                // Workaround for Robolectric running measure/layout
                // calls inline rather than allowing them to be posted
                // as they would on a real device.
                mCurTransaction.commitAllowingStateLoss();
            }
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    private Fragment getFragment(int position) {
        if (!mCacheFragments.isEmpty()) {
            Log.i(TAG, "getFragment 1");
            // 切记，这里必须用 pollFirst，也就是按照队列的先进先出规则
            // 原因： 在滑动页面的时候，我们认为 ViewPager 对 Fragment 进行切换，这没有错，但 Fragment 并不是 View，所以实际上还是对 View
            // 进行切换，在 View 已经附着在 ViewPager 上以后，ViewPager 就不会向 Fragment 索取View，而是直接对 View 进行切换，这个切换是
            // 按照 View 添加的位置和顺序进行的，所以这里必须用 pollFirst 才能保持 View 和 Fragment 的对应。
            return mCacheFragments.pollFirst();
        }
        Log.i(TAG, "getFragment 2");
        return onCreateFragment(position);
    }

    @NonNull
    protected abstract T getModel(int position);

    @NonNull
    protected abstract Fragment onCreateFragment(int position);
}
