package com.lacourt.githubusers.repository

import com.lacourt.githubusers.network.GithubApiService

open class Repository(private val service: GithubApiService) {

    suspend fun getUserList(since: Int, perPage: Int) = service.getUserList(since, perPage)

    suspend fun getUserDetails(username: String) = service.getUserDetails(username)

    suspend fun getUserRepoList(username: String) = service.getUserRepoList(username )

    suspend fun searchUser(username: String) = service.searchUser(username)
}