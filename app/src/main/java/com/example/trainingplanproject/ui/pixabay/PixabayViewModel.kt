package com.example.trainingplanproject.ui.pixabay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.trainingplanproject.network.PixabayApi
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.network.service.PixabayApiService
import com.example.trainingplanproject.paging.PixabayPagingSource
import com.example.trainingplanproject.util.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PixabayViewModel() : ViewModel() {

    private val _pixabayList = MutableLiveData<List<PixabayItem>>()
    val pixabayList: LiveData<List<PixabayItem>>
        get() = _pixabayList

    val listData = Pager(PagingConfig(pageSize = 1)) {
        PixabayPagingSource(PixabayApi.pixabayApiService)
    }.flow.cachedIn(viewModelScope)

    fun getPixabayList() {
        viewModelScope.launch(Dispatchers.IO) {
            request(
                request = { PixabayApi.pixabayApiService.getPixabayPicture() },
                onSuccess = {
                    Log.e(">>>", "success, res = ${it.body().toString()}")
                },
                onError = {
                    Log.e(">>>", "error, e = $it")
                }
            )
        }
    }
}
