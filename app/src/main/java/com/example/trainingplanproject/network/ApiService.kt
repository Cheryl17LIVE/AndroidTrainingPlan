package com.example.trainingplanproject.network

import com.example.trainingplanproject.network.service.PixabayApiService
import com.example.trainingplanproject.network.user.GitUserApiService
import com.example.trainingplanproject.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.PIXABAY_URL)
        .build()

object PixabayApi {
    val pixabayApiService : PixabayApiService by lazy { retrofit.create(PixabayApiService::class.java) }
}
