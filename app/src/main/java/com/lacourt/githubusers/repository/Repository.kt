package com.lacourt.githubusers.repository

import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError

class Repository(private val service: GithubApiService) {
    suspend fun fetchUsersList(): List<UserListed>? {
        val response = service.getUserList(0, 25)
        return when(response) {
            is Success -> response.body.asDomainModel()
            is ApiError -> null
            is NetworkError -> null
            is UnknownError -> null
        }
    }
}