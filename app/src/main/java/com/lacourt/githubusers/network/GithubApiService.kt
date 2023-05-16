package com.lacourt.githubusers.network

import com.lacourt.githubusers.network.dto.SearchResultDTO
import com.lacourt.githubusers.network.dto.UserDetailsDTO
import com.lacourt.githubusers.network.dto.UserListedDTO
import com.lacourt.githubusers.network.dto.UserRepositoryDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("users")
    suspend fun getUserList(@Query("since") since: Int, @Query("per_page") perPage: Int): NetworkResponse<List<UserListedDTO>, RequestError>

    @GET("users/{user}")
    suspend fun getUserDetails(@Path("user") user: String): NetworkResponse<UserDetailsDTO, RequestError>

    @GET("users/{user}/repos?per_page=25")
    suspend fun getUserRepoList(@Path("user") user: String, @Query("page") page: Int): NetworkResponse<List<UserRepositoryDTO>, RequestError>

    @GET("/search/users")
    suspend fun searchUser(@Query("q") username: String): NetworkResponse<SearchResultDTO, RequestError>
}