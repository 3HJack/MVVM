package com.nb.core.test.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.hhh.onepiece.R
import com.nb.core.BaseFragment
import com.nb.core.PagerAdapter
import com.nb.core.PagerFragment
import com.nb.core.test.explore.ExploreFragment
import com.nb.core.test.live.LiveFragment
import com.nb.core.test.subscribe.SubscribeFragment

class HomeFragment : PagerFragment<String>() {

    override val smoothScroll: Boolean
        get() = true
    override fun onCreateViewModel() = ViewModelProvider(this).get(HomeViewModel::class.java)

    override fun onCreatePagerAdapter() = object : PagerAdapter<String>(this) {

        override val isMutable: Boolean
            get() = true

        override fun onCreateFragment(position: Int): BaseFragment {
            return when (position) {
                0 -> SubscribeFragment()
                1 -> ExploreFragment()
                2 -> LiveFragment()
                3 -> ExploreFragment()
                else -> throw RuntimeException("invalid position!!!")
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerView.currentItem = 1
    }

    override fun onCreateTabConfigurationStrategy() =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.setCustomView(R.layout.home_tab_item_layout)
            val imageView: ImageView = tab.customView!!.findViewById(R.id.tab_icon)
            val textView: TextView = tab.customView!!.findViewById(R.id.tab_text)
            when (position) {
                0 -> {
                    imageView.setImageResource(R.drawable.home_tab_icon_subscribe)
                    textView.text = viewModel.getItem(0)
                }
                1 -> {
                    imageView.setImageResource(R.drawable.home_tab_icon_explore)
                    textView.text = viewModel.getItem(1)
                }
                2 -> {
                    imageView.setImageResource(R.drawable.home_tab_icon_live)
                    textView.text = viewModel.getItem(2)
                }
                3 -> {
                    imageView.setImageResource(R.drawable.home_tab_icon_explore)
                    textView.text = viewModel.getItem(3)
                }
            }
        }
}