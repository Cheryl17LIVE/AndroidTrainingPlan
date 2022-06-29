package com.example.trainingplanproject.ui.pixabay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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

    private var pixabayLayoutStyle: PixabayLayoutStyle = PixabayLayoutStyle.GRID

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
        pixabayLayoutStyle = style
        binding.styleControl.setImageResource(drawable)
        binding.recyclerView.layoutManager = when (style) {
            PixabayLayoutStyle.LINEAR -> LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            PixabayLayoutStyle.GRID -> GridLayoutManager(context, 3)
        }
        pixabayAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        //picture
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
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

        binding.searchEditText.setOnFocusChangeListener { view, isFocus ->
            if (isFocus) {
                viewModel.getHistoryListData()
                binding.rvSearch.visibility = View.VISIBLE
            } else {
                binding.rvSearch.visibility = View.GONE
            }
        }

        binding.searchEditText.setOnClickListener {
            viewModel.getHistoryListData()
            binding.rvSearch.visibility = View.VISIBLE
        }

        binding.styleControl.setOnClickListener {
            when (pixabayLayoutStyle) {
                PixabayLayoutStyle.GRID -> setLayoutStyle(PixabayLayoutStyle.LINEAR, R.drawable.ic_linear)
                PixabayLayoutStyle.LINEAR -> setLayoutStyle(PixabayLayoutStyle.GRID, R.drawable.ic_grid)
            }
        }
    }

    private fun searchWord() {
        hideKeyboard()
        binding.rvSearch.visibility = View.GONE
        val query = binding.searchEditText.text.toString()
        if (query.trim().isNotEmpty()) viewModel.storeSearchWord(query)
        lifecycleScope.launch {
            viewModel.searchListData(query).collect { pagingData ->
                pixabayAdapter.submitData(pagingData)
            }
        }
    }

    private fun initObserver() {
        viewModel.historyList.observe(viewLifecycleOwner) {
            searchHistoryAdapter.submitList(it)
        }
    }


}