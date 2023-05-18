package com.lacourt.githubusers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.paging.UserListPageSource

open class Repository(private val service: GithubApiService) {

    val userList = Pager(PagingConfig(pageSize = 25, initialLoadSize = 25)) {
                UserListPageSource(service)
    }.flow

    suspend fun getUserDetails(username: String) = service.getUserDetails(username)

    suspend fun getUserRepoList(username: String, page: Int) = service.getUserRepoList(username, page)

    suspend fun searchUser(username: String) = service.searchUser(username)
}