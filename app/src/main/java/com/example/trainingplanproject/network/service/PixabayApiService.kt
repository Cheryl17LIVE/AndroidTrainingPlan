package com.example.trainingplanproject.network.service

import com.example.trainingplanproject.network.model.pixabay.PixabayResponse
import com.example.trainingplanproject.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {

    @GET(Constants.PIXABAY_URL)
    suspend fun getPixabayPicture(
        @Query("key") key: String ?= Constants.PIXABAY_API_KEY,
        @Query("q") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") per_page: Int? = 10,
    ): Response<PixabayResponse>

}