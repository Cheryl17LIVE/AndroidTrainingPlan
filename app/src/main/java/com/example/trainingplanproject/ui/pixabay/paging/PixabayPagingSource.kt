package com.example.trainingplanproject.ui.pixabay.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.network.service.PixabayApiService
import retrofit2.HttpException
import java.io.IOException

class PixabayPagingSource(private val apiService: PixabayApiService, private val query: String? = null) : PagingSource<Int, PixabayItem>() {
    override fun getRefreshKey(state: PagingState<Int, PixabayItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixabayItem> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getPixabayPicture(page = currentPage, query = query)
            val data = response.body()?.pixabayItems ?: emptyList()
            val responseData = mutableListOf<PixabayItem>()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage > 0) -1 else null,
                nextKey = currentPage.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}