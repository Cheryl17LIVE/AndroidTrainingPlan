package com.example.trainingplanproject.ui.pixabay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trainingplanproject.databinding.FragmentPixabayBinding
import com.example.trainingplanproject.ui.base.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PixabayFragment : BaseBindingFragment<FragmentPixabayBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPixabayBinding =
        FragmentPixabayBinding::inflate

    private val viewModel: PixabayViewModel by viewModels()

    private val adapter by lazy { PixabayPagingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        initData()
//        initObserver()
    }

    private fun initData() {
        lifecycleScope.launch {
            viewModel.listData.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
//        Log.e(">>>", "getPixabayList")
//        viewModel.getPixabayList()
    }

    private fun initObserver() {
//        viewModel.gitUserList.observe(viewLifecycleOwner) {
//            hideLoading()
//            adapter.addFooterAndSubmitList(it)
//        }
    }

    private fun initLayout() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerView.adapter = adapter
    }


}