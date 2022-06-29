package com.example.trainingplanproject.ui.pixabay.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.PagingSource
import com.example.trainingplanproject.network.ApiService
import com.example.trainingplanproject.paging.PixabayPagingSource
import com.example.trainingplanproject.ui.pixabay.PixabayFragment

class PixabayRepository(private val androidContext: Context, private val apiService: ApiService)  {
    companion object {
        const val MY_SP = "MY_SP"
        const val LAYOUT_STYLE = "LAYOUT_STYLE"
    }

    private val sharedPref: SharedPreferences by lazy {
        androidContext.getSharedPreferences(MY_SP, Context.MODE_PRIVATE)
    }

    var layoutStyle
        get() = sharedPref.getInt(LAYOUT_STYLE, PixabayFragment.PixabayLayoutStyle.GRID.ordinal)
        set(value) {
            with(sharedPref.edit()) {
                putInt(LAYOUT_STYLE, value)
                apply()
            }
        }


    fun getPagingSource(query: String?): PixabayPagingSource {
        return PixabayPagingSource(apiService.createPixabayService(), query)
    }

}