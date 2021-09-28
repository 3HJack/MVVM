package com.nb.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

abstract class PagerFragment<MODEL> : BaseFragment() {

    protected lateinit var tabView: TabLayout
    protected lateinit var pagerView: ViewPager2

    protected open val smoothScroll = false
    override val layoutId = R.layout.pager_layout

    val viewModel by lazy { onCreateViewModel() }
    protected lateinit var pagerAdapter: PagerAdapter<MODEL>

    protected abstract fun onCreateViewModel(): PagerViewModel<MODEL>

    protected abstract fun onCreatePagerAdapter(): PagerAdapter<MODEL>

    protected abstract fun onCreateTabConfigurationStrategy(): TabLayoutMediator.TabConfigurationStrategy

    fun getCurrentFragment() = pagerAdapter.getFragment(pagerView.currentItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPagerView(view)
        initTabView(view)
        observePageState()
    }

    private fun initPagerView(view: View) {
        pagerView = view.findViewById(R.id.pager_view)
        pagerAdapter = onCreatePagerAdapter()
        pagerView.adapter = pagerAdapter
        pagerView.offscreenPageLimit = 1
    }

    private fun initTabView(view: View) {
        tabView = view.findViewById(R.id.tab_view)
        TabLayoutMediator(
            tabView,
            pagerView,
            true,
            smoothScroll,
            onCreateTabConfigurationStrategy()
        ).attach()
        tabView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pagerAdapter.getFragment(tab.position)?.onPageSelected()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                pagerAdapter.getFragment(tab.position)?.onPageUnselected()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                pagerAdapter.getFragment(tab.position)?.onPageReselected()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observePageState() {
        viewModel.state.observe(viewLifecycleOwner) { pagerAdapter.notifyDataSetChanged() }
    }
}