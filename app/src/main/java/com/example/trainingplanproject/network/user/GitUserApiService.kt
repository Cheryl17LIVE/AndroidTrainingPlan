package com.example.trainingplanproject.network.user

import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query


interface GitUserApiService {
    @GET("users")
    suspend fun getGitUserList(
        @Query("accept") contentType: String? = "application/vnd.github.v3+json",
        @Query("since") since: String? = null,
        @Query("per_page") itemsPerPage: Int? = null,
    ): List<GitUser>

    @PATCH("user")
    suspend fun getGitUser(): List<GitUser>

    @GET("users/{login}")
    suspend fun getUserDetail(
        @Path("login") login: String,
    ): UserDetail

}
