package com.example.trainingplanproject.ui.pixabay.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trainingplanproject.db.SearchHistoryDao
import com.example.trainingplanproject.db.SearchHistoryData
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.ui.pixabay.PixabayFragment
import com.example.trainingplanproject.ui.pixabay.repository.PixabayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PixabayViewModel(
    private val searchHistoryDao: SearchHistoryDao,
    private val repo: PixabayRepository
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

    val historyList: LiveData<List<SearchHistoryData>>
        get() = _historyList

    private val _historyList = MutableLiveData<List<SearchHistoryData>>()

    fun getHistoryListData() {
        viewModelScope.launch(Dispatchers.IO) {
            _historyList.postValue(searchHistoryDao.getAll())
        }
    }

    fun getLayoutStyle(): Int {
        return repo.layoutStyle
    }

    fun storeLayoutStyle(layoutStyle: PixabayFragment.PixabayLayoutStyle) {
        repo.layoutStyle = layoutStyle.ordinal
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
            repo.getPagingSource(query)
        }.flow.cachedIn(viewModelScope)
        return result
    }

}
