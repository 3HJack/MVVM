package com.hhh.onepiece.explore;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.hhh.mvvm.base.BaseFragment;
import com.hhh.mvvm.recycler.LoadingStatus;
import com.hhh.mvvm.recycler.RecyclerFragment;
import com.hhh.onepiece.R;
import com.hhh.onepiece.main.WidgetUtils;
import com.hhh.onepiece.model.WorksModel;
import com.hhh.onepiece.model.WorksModelGenerator;
import com.hhh.onepiece.view.BannerView;
import com.hhh.onepiece.view.NetworkImageView;

import java.util.List;

public class ExploreHeadFragment extends BaseFragment {

  private BannerView mBannerView;
  private TextView mTitleView;

  private List<WorksModel> mWorksModels;

  @Override
  protected int getLayoutResId() {
    return R.layout.home_explore_head_layout;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBannerView = findViewById(R.id.banner_view);
    mTitleView = findViewById(R.id.title);

    mBannerView.getLayoutParams().height =
        getResources().getDisplayMetrics().widthPixels * 3 / 4
            + WidgetUtils.dip2px(getContext(), 21f);
    RelativeLayout.LayoutParams layoutParams =
        (RelativeLayout.LayoutParams) mBannerView.getIndicatorView().getLayoutParams();
    layoutParams.bottomMargin = WidgetUtils.dip2px(getContext(), 8.5f);
    mBannerView.setPageIndicator(
        new int[]{R.drawable.home_indicator_normal, R.drawable.home_indicator_selected});

    monitorPageRefreshStatus();
  }

  @Override
  public void onResume() {
    super.onResume();
    mBannerView.startTurning();
  }

  @Override
  public void onPause() {
    super.onPause();
    mBannerView.stopTurning();
  }

  private void monitorPageRefreshStatus() {
    ((RecyclerFragment) getParentFragment()).getViewModel().getRefreshLiveData().observe(getViewLifecycleOwner(),
        (Observer<LoadingStatus>) loadingState -> {
          if (loadingState.mStatus == LoadingStatus.Status.RUNNING) {
            mWorksModels = WorksModelGenerator.createWorksModels(3);
            refreshUI(mWorksModels);
          }
        });
  }

  private void refreshUI(List<WorksModel> modelList) {
    getView().setVisibility(View.VISIBLE);
    mTitleView.setText(modelList.get(0).mText);
    mBannerView.setPages(new CBViewHolderCreator() {
      @Override
      public Holder createHolder(View itemView) {
        return new BannerHolder(itemView);
      }

      @Override
      public int getLayoutId() {
        return R.layout.home_banner_item_layout;
      }
    }, modelList).setOnPageChangeListener(new OnPageChangeListener() {

      private int originIndex;

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int i) {
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int i, int i1) {
      }

      @Override
      public void onPageSelected(int index) {
        if (originIndex != index) {
          mTitleView.setText(modelList.get(index).mText);
          originIndex = index;
        }
      }
    });
  }

  public static class BannerHolder extends Holder<WorksModel> {

    private NetworkImageView mCoverView;

    public BannerHolder(View itemView) {
      super(itemView);
    }

    @Override
    protected void initView(View itemView) {
      mCoverView = itemView.findViewById(R.id.cover);
    }

    @Override
    public void updateUI(WorksModel data) {
      mCoverView.getHierarchy().setPlaceholderImage(new ColorDrawable(data.mColor));
    }
  }
}
