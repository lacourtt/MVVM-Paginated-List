package com.lacourt.githubusers.network

import com.lacourt.githubusers.network.dto.User
import com.lacourt.githubusers.network.dto.UserListed
import com.lacourt.githubusers.network.dto.UserRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users")
    suspend fun getUserList(): NetworkResponse<List<UserListed>, RequestError>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): NetworkResponse<User, RequestError>

    @GET("users/{user}/repos")
    suspend fun getUserRepoList(@Path("user") user: String): NetworkResponse<List<UserRepository>, RequestError>
}