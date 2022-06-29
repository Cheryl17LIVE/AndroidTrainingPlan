package com.example.trainingplanproject.network.service

import com.example.trainingplanproject.network.model.pixabay.PixabayItem
import com.example.trainingplanproject.network.model.pixabay.PixabayResponse
import com.example.trainingplanproject.network.user.GitUser
import com.example.trainingplanproject.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {

    @GET(Constants.PIXABAY_URL)
    suspend fun getPixabayPicture(
        @Query("key") key: String ?= Constants.PIXABAY_API_KEY,
        @Query("q") query: String? = null,
//        @Query("category") category: String? = null, //backgrounds, fashion, nature, science, education, feelings, health, people, religion, places, animals, industry, computer, food, sports, transportation, travel, buildings, business, music
        @Query("page") page: Int? = null,
        @Query("per_page") per_page: Int? = 10,
    ): Response<PixabayResponse>

}