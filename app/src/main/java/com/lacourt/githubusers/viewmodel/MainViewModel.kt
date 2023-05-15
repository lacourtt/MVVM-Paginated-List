package com.lacourt.githubusers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.paging.UserListPageSource

class MainViewModel(private val service: GithubApiService): ViewModel() {

    val userList = Pager(PagingConfig(pageSize = 25, initialLoadSize = 25)) {
        UserListPageSource(service)
    }.flow.cachedIn(viewModelScope)

}