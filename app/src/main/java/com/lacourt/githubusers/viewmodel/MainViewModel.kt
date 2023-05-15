package com.lacourt.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.paging.UserListPageSource
import com.lacourt.githubusers.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, private val service: GithubApiService): ViewModel() {
//    private val _usersList = MutableLiveData<List<UserListed>>()
//    val usersList: LiveData<List<UserListed>> = _usersList

    val userList = Pager(PagingConfig(pageSize = 25, initialLoadSize = 25)) {
        UserListPageSource(service)
    }.flow.cachedIn(viewModelScope)

//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            fetchUsersList()
//        }
//    }

//    private suspend fun fetchUsersList() {
//        userList.postValue(repository.fetchUsersList())
//    }
}