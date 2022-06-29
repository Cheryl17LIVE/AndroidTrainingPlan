package com.example.trainingplanproject.ui.pixabay.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trainingplanproject.db.SearchHistoryDao
import com.example.trainingplanproject.db.SearchHistoryData
import com.example.trainingplanproject.network.PixabayApi
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.paging.PixabayPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PixabayViewModel(private val searchHistoryDao: SearchHistoryDao) : ViewModel() {

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

    val historyList: LiveData<List<SearchHistoryData>>
            get() = _historyList// = searchHistoryDao.getAll().asLiveData() //List<SearchHistoryData>

    private val _historyList = MutableLiveData<List<SearchHistoryData>>()

    fun getHistoryListData() {
        viewModelScope.launch(Dispatchers.IO) {
            _historyList.postValue(searchHistoryDao.getAll())
        }
    }

    fun storeSearchWord(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryDao.insert(SearchHistoryData(query = query))
            _historyList.postValue(searchHistoryDao.getAll())
        }
    }

    fun deleteSearchWord(data: SearchHistoryData) {
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryDao.delete(data)
            _historyList.postValue(searchHistoryDao.getAll())
        }
    }

    fun searchListData(query: String? = null): Flow<PagingData<PixabayItem>> {
        val result = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            PixabayPagingSource(PixabayApi.pixabayApiService, query)
        }.flow.cachedIn(viewModelScope)
        return result
    }

}
