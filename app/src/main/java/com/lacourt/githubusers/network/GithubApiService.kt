package com.lacourt.githubusers.network

import com.lacourt.githubusers.network.dto.UserDTO
import com.lacourt.githubusers.network.dto.UserListedDTO
import com.lacourt.githubusers.network.dto.UserRepositoryDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("users")
    suspend fun getUserList(): NetworkResponse<List<UserListedDTO>, RequestError>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): NetworkResponse<UserDTO, RequestError>

    @GET("users/{user}/repos")
    suspend fun getUserRepoList(@Path("user") user: String): NetworkResponse<List<UserRepositoryDTO>, RequestError>
}