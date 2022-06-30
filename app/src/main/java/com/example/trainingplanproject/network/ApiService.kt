package com.example.trainingplanproject.network

import com.example.trainingplanproject.network.service.PixabayApiService
import com.example.trainingplanproject.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class ApiService(private val okHttpClient: OkHttpClient) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.PIXABAY_URL)
        .client(okHttpClient)
        .build()

    fun createPixabayService(): PixabayApiService = retrofit.create(PixabayApiService::class.java)

}

class OkHttpClientProvider {

    fun createOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpBuilder = OkHttpClient().newBuilder()
        httpBuilder.connectTimeout(300, TimeUnit.SECONDS)
        httpBuilder.readTimeout(300, TimeUnit.SECONDS)
        httpBuilder.callTimeout(300, TimeUnit.SECONDS)
        httpBuilder.addInterceptor(logInterceptor)
        return httpBuilder.build()
    }
}