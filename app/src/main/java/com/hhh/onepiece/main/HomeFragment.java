package com.hhh.onepiece.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.tabs.TabLayout;
import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.multi.MultiFragment;
import com.hhh.mvvm.multi.MultiPagerAdapter;
import com.hhh.onepiece.R;
import com.hhh.onepiece.explore.ExploreFragment;
import com.hhh.onepiece.live.LiveFragment;
import com.hhh.onepiece.subscribe.SubscribeFragment;

public class HomeFragment extends MultiFragment {

  private DrawerLayout mDrawerLayout;

  @Override
  protected int getLayoutResId() {
    return R.layout.home_layout;
  }

  @Override
  protected MultiPagerAdapter onCreatePagerAdapter() {
    return new MultiPagerAdapter(getChildFragmentManager(), R.id.view_pager, 3) {
      @NonNull
      @Override
      protected BaseFragment onCreateFragment(int position) {
        if (position == 0) {
          return new SubscribeFragment();
        } else if (position == 1) {
          return new ExploreFragment();
        } else if (position == 2) {
          return new LiveFragment();
        } else {
          throw new RuntimeException("invalid position!!!");
        }
      }
    };
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mDrawerLayout = (DrawerLayout) view;
    mViewPager.setCurrentItem(1);
    initBottomBar();
    findViewById(R.id.top_bar_right)
        .setOnClickListener(view1 -> mDrawerLayout.openDrawer(Gravity.RIGHT));
  }

  private void initBottomBar() {
    initTabView(mTabLayout.getTabAt(0), R.drawable.home_tab_icon_subscribe,
        R.string.universal_subscribe);
    initTabView(mTabLayout.getTabAt(1), R.drawable.home_tab_icon_explore, R.string.home_explore);
    initTabView(mTabLayout.getTabAt(2), R.drawable.home_tab_icon_live, R.string.home_live);
  }

  private void initTabView(TabLayout.Tab tab, int drawableId, int textId) {
    tab.setCustomView(R.layout.home_tab_item_layout);
    View view = tab.getCustomView();
    ((ImageView) view.findViewById(R.id.tab_icon)).setImageResource(drawableId);
    ((TextView) view.findViewById(R.id.tab_text)).setText(textId);
  }
}
