package com.nb.core.test.main

import com.nb.core.PagerViewModel

class HomeViewModel : PagerViewModel<String>() {

    override fun onCreateItems() = mutableListOf("订阅", "发现", "直播")

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return itemId in 0 until size
    }
}