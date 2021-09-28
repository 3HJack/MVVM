package com.nb.core

import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.ref.WeakReference

abstract class PagerAdapter<MODEL>(private val fragment: PagerFragment<MODEL>) :
    FragmentStateAdapter(fragment) {

    protected open val isMutable = false
    private val fragmentMap = mutableMapOf<Int, WeakReference<BaseFragment>>()

    override fun getItemCount() = fragment.viewModel.size

    abstract fun onCreateFragment(position: Int): BaseFragment

    fun getFragment(position: Int) = fragmentMap[position]?.get()

    final override fun createFragment(position: Int) = onCreateFragment(position).also {
        fragmentMap[position] = WeakReference(it)
    }

    override fun getItemId(position: Int) =
        if (isMutable) fragment.viewModel.getItemId(position) else super.getItemId(position)

    override fun containsItem(itemId: Long) =
        if (isMutable) fragment.viewModel.containsItem(itemId) else super.containsItem(itemId)
}