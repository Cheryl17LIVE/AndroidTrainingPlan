package com.example.trainingplanproject.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.network.service.PixabayApiService
import java.lang.Exception

class PixabayPagingSource(private val apiService: PixabayApiService): PagingSource<Int, PixabayItem>() {
    override fun getRefreshKey(state: PagingState<Int, PixabayItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixabayItem> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getPixabayPicture(page = currentPage)
            val data = response.body()?.pixabayItems ?: emptyList()
            val responseData = mutableListOf<PixabayItem>()
            responseData.addAll(data)
            Log.e(">>>", "")
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}