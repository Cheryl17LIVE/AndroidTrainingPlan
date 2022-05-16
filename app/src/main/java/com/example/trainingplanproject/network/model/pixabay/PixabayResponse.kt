package com.example.trainingplanproject.network.model.pixabay


import com.squareup.moshi.Json

data class PixabayResponse(
    @Json(name = "hits")
    val pixabayItems: List<PixabayItem>,
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalHits")
    val totalHits: Int
)