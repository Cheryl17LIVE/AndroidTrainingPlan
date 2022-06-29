package com.example.trainingplanproject.ui.pixabay

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingplanproject.R
import com.example.trainingplanproject.databinding.FragmentPixabayBinding
import com.example.trainingplanproject.ui.base.BaseBindingFragment
import kotlinx.coroutines.launch


class PixabayFragment : BaseBindingFragment<FragmentPixabayBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPixabayBinding =
        FragmentPixabayBinding::inflate

    private val viewModel: PixabayViewModel by viewModels()

    private val pixabayAdapter by lazy { PixabayPagingAdapter() }

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
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = pixabayAdapter.withLoadStateHeaderAndFooter(
            header = PixabayLoadStateAdapter(pixabayAdapter),
            footer = PixabayLoadStateAdapter(pixabayAdapter)
        )

        pixabayAdapter.addLoadStateListener { loadStates ->
            when (loadStates.refresh) {
                is LoadState.Loading -> loading()
                else -> hideLoading()
            }
            binding.swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        searchWord()

    }

    private fun initAction() {
        binding.swipeRefreshLayout.setOnRefreshListener { searchWord() }
        binding.searchAction.setOnClickListener {
            searchWord()
            hideKeyboard()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {searchWord()
                searchWord()
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.styleControl.setOnClickListener {
            when (pixabayLayoutStyle) {
                PixabayLayoutStyle.GRID -> setLayoutStyle(PixabayLayoutStyle.LINEAR, R.drawable.ic_linear)
                PixabayLayoutStyle.LINEAR -> setLayoutStyle(PixabayLayoutStyle.GRID, R.drawable.ic_grid)
            }
        }
    }

    private fun searchWord() {
        val query = binding.searchEditText.text.toString()
        lifecycleScope.launch {
            viewModel.searchListData(query).collect { pagingData ->
                pixabayAdapter.submitData(pagingData)
            }
        }
    }

    private fun initObserver() {

//        viewModel.loading.observe(viewLifecycleOwner) {
//            if (it == true) loading() else hideLoading()
//        }

//        viewModel.gitUserList.observe(viewLifecycleOwner) {
//            hideLoading()
//            adapter.addFooterAndSubmitList(it)
//        }
    }


}