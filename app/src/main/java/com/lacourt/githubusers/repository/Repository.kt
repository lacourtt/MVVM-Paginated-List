package com.lacourt.githubusers.repository

import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError
import com.lacourt.githubusers.network.dto.UserListed

class Repository(private val service: GithubApiService) {
    suspend fun fetchUsersList(): List<UserListed>? {
        val response = service.getUserList()
        return when(response) {
            is Success -> response.body
            is ApiError -> null
            is NetworkError -> null
            is UnknownError -> null
        }
    }
}