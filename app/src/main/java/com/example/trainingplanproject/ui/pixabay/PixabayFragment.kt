package com.example.trainingplanproject.ui.pixabay

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingplanproject.R
import com.example.trainingplanproject.databinding.FragmentPixabayBinding
import com.example.trainingplanproject.ui.base.BaseBindingFragment
import com.example.trainingplanproject.ui.pixabay.adapter.OnItemClickListener
import com.example.trainingplanproject.ui.pixabay.adapter.PixabayPagingAdapter
import com.example.trainingplanproject.ui.pixabay.adapter.SearchHistoryListAdapter
import com.example.trainingplanproject.ui.pixabay.viewmodel.PixabayViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PixabayFragment : BaseBindingFragment<FragmentPixabayBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPixabayBinding =
        FragmentPixabayBinding::inflate

    private val viewModel: PixabayViewModel by viewModel()

    private val pixabayAdapter by lazy { PixabayPagingAdapter() }

    private val searchHistoryAdapter by lazy {
        SearchHistoryListAdapter(OnItemClickListener({
            binding.searchEditText.setText(it)
            searchWord()
        }, {
            viewModel.deleteSearchWord(it)
        }))
    }

    enum class PixabayLayoutStyle {
        GRID, LINEAR
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        initAdapter()
        initAction()
        initObserver()
    }

    private fun initLayout() {
    }

    private fun setLayoutStyle(style: PixabayLayoutStyle, @DrawableRes drawable: Int) {
        viewModel.storeLayoutStyle(style)
        binding.styleControl.setImageResource(drawable)
        binding.recyclerView.layoutManager = when (style) {
            PixabayLayoutStyle.LINEAR -> LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            PixabayLayoutStyle.GRID -> GridLayoutManager(context, 3)
        }
        pixabayAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        //picture
        when (viewModel.getLayoutStyle()) {
            PixabayLayoutStyle.GRID.ordinal -> setLayoutStyle(PixabayLayoutStyle.GRID, R.drawable.ic_grid)
            PixabayLayoutStyle.LINEAR.ordinal -> setLayoutStyle(PixabayLayoutStyle.LINEAR, R.drawable.ic_linear)
        }
        pixabayAdapter.addLoadStateListener { loadStates ->
            when (loadStates.refresh) {
                is LoadState.Loading -> loading()
                else -> hideLoading()
            }
            binding.swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
        }
        binding.recyclerView.adapter = pixabayAdapter
        searchWord()

        //search
        binding.rvSearch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        binding.rvSearch.adapter = searchHistoryAdapter

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAction() {
        binding.swipeRefreshLayout.setOnRefreshListener { searchWord() }
        binding.searchAction.setOnClickListener {
            searchWord()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchWord()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.searchEditText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.getHistoryListData()
                isShowSearchHistory(true)
            }
            return@setOnTouchListener false
        }

        binding.styleControl.setOnClickListener {
            isShowSearchHistory(false)
            when (viewModel.getLayoutStyle()) {
                PixabayLayoutStyle.GRID.ordinal -> setLayoutStyle(PixabayLayoutStyle.LINEAR, R.drawable.ic_linear)
                PixabayLayoutStyle.LINEAR.ordinal -> setLayoutStyle(PixabayLayoutStyle.GRID, R.drawable.ic_grid)
            }
        }
    }

    private fun searchWord() {
        hideKeyboard()
        isShowSearchHistory(false)
        val query = binding.searchEditText.text.toString()
        if (query.trim().isNotEmpty()) viewModel.storeSearchWord(query)
        lifecycleScope.launch {
            viewModel.searchListData(query).collect { pagingData ->
                pixabayAdapter.submitData(pagingData)
            }
        }
    }

    private fun isShowSearchHistory(visible: Boolean) {
        binding.rvSearch.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun initObserver() {
        viewModel.historyList.observe(viewLifecycleOwner) {
            searchHistoryAdapter.submitList(it)
        }
    }


}