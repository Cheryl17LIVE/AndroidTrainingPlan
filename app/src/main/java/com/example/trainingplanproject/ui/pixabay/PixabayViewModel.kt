package com.example.trainingplanproject.ui.pixabay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trainingplanproject.network.PixabayApi
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.paging.PixabayPagingSource
import kotlinx.coroutines.flow.Flow

class PixabayViewModel : ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

/*
    enum class PixabayLayoutStyle {
        GRID, LINEAR
    }

    private val _pixabayLayoutStyle = MutableLiveData<PixabayLayoutStyle>()
    val pixabayLayoutStyle: LiveData<PixabayLayoutStyle>
        get() = _pixabayLayoutStyle

    val listData = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        PixabayPagingSource(PixabayApi.pixabayApiService)
    }.flow.cachedIn(viewModelScope)
*/

    fun searchListData(query: String? = null): Flow<PagingData<PixabayItem>> {
        val result = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            PixabayPagingSource(PixabayApi.pixabayApiService, query)
        }.flow.cachedIn(viewModelScope)
        return result
    }
}
