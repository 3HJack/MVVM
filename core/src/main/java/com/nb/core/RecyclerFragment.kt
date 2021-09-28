package com.nb.core

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.flow.collectLatest

abstract class RecyclerFragment<MODEL : DiffCallback<MODEL>, PARAMETER> : BaseFragment() {

    protected lateinit var recyclerView: RecyclerView
    protected lateinit var refreshView: SwipeRefreshLayout

    protected lateinit var concatAdapter: ConcatAdapter
    protected var headerAdapter: HeaderAdapter? = null
    protected var footerAdapter: FooterAdapter? = null

    val viewModel by lazy { onCreateViewModel() }
    val recyclerAdapter by lazy { onCreateRecyclerAdapter() }

    override val layoutId = R.layout.recycler_layout

    protected open fun enableRefresh() = true

    protected open fun enableLoadMore() = true

    protected open fun onCreateItemDecoration(): RecyclerView.ItemDecoration? = null

    protected open fun onCreateLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(requireContext())

    protected open fun onCreateUnusualFragment() = UnusualFragment()

    protected open fun getParameter(): PARAMETER? = null

    protected open fun onCreateHeaderAdapter(): HeaderAdapter? = null

    protected open fun onCreateFooterAdapter(): FooterAdapter? = FooterAdapter()

    protected abstract fun onCreateRecyclerAdapter(): RecyclerAdapter<MODEL>

    protected abstract fun onCreateViewModel(): RecyclerViewModel<MODEL, PARAMETER>

    fun isVisibleToUser(): Boolean {
        val fragment = parentFragment
        return if (fragment is PagerFragment<*>) fragment.getCurrentFragment() === this else true
    }

    fun isEmptyPage(): Boolean {
        return recyclerAdapter.itemCount == 0
    }

    override fun onPageSelected() {
        if (isEmptyPage()) {
            viewModel.load(getParameter())
        }
        if (headerAdapter != null && headerAdapter != concatAdapter.adapters[0]) {
            concatAdapter.addAdapter(0, headerAdapter!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        initRefreshView(view)
        initEmptyView(view)
        initViewModel()
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = onCreateLayoutManager()
        onCreateItemDecoration()?.let { recyclerView.addItemDecoration(it) }
        recyclerAdapter.inject(this)
        headerAdapter = onCreateHeaderAdapter()?.also { it.inject(this) }
        if (enableLoadMore()) {
            footerAdapter = onCreateFooterAdapter()?.also { adapter ->
                adapter.inject(this)
                recyclerAdapter.addLoadStateListener { adapter.loadState = it.append }
            }
        }
        concatAdapter = ConcatAdapter(
            listOfNotNull(
                if (isVisibleToUser()) headerAdapter else null,
                recyclerAdapter,
                footerAdapter
            )
        )
        recyclerView.adapter = concatAdapter
    }

    private fun initRefreshView(view: View) {
        refreshView = view.findViewById(R.id.refresh_view)
        refreshView.isEnabled = enableRefresh()
        refreshView.setOnRefreshListener { recyclerAdapter.refresh() }
        viewLifecycleScope.launchWhenCreated {
            recyclerAdapter.loadStateFlow.collectLatest { loadStates ->
                refreshView.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun initEmptyView(view: View) {
        val unusualFragment = onCreateUnusualFragment()
        recyclerAdapter.addLoadStateListener { unusualFragment.loadState = it.refresh }
        childFragmentManager.beginTransaction()
            .replace(R.id.unusual_view, unusualFragment, unusualFragment.javaClass.name).commit()
    }

    private fun initViewModel() {
        viewModel.loadLiveData.observe(viewLifecycleOwner) {
            viewLifecycleScope.launchWhenCreated {
                recyclerAdapter.submitData(it)
            }
        }
        if (isVisibleToUser()) {
            viewModel.load(getParameter())
        }
    }
}