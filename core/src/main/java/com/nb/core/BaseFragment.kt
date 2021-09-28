package com.nb.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment : Fragment(), OnPageSelectedListener {

    val viewLifecycle by lazy { viewLifecycleOwner.lifecycle }
    val viewLifecycleScope by lazy { viewLifecycleOwner.lifecycleScope }

    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = if (layoutId != 0) inflater.inflate(layoutId, container, false) else null

    fun finishActivity() {
        if (isAdded) {
            requireActivity().finish()
        }
    }

    fun onBackPressed() {
        if (isAdded) {
            requireActivity().onBackPressed()
        }
    }
}