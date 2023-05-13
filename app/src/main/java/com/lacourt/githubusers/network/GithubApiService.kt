package com.lacourt.githubusers.network

import com.lacourt.githubusers.view.dto.User
import com.lacourt.githubusers.view.dto.UserRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users")
    suspend fun getUserList(): List<User>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): User

    @GET("users/{user}/repos")
    suspend fun getUserRepoList(@Path("user") user: String): List<UserRepository>
}