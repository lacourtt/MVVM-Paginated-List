package com.lacourt.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserDetails
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.model.UserRepository
import com.lacourt.githubusers.repository.Repository
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError
import com.lacourt.githubusers.paging.RepoListPageSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val userListFlow: Flow<PagingData<UserListed>> = repository.userList

    val repositoryList : Flow<PagingData<UserRepository>> = Pager(PagingConfig(pageSize = 25, initialLoadSize = 25)) {
        RepoListPageSource(repository, userName!!)
    }.flow.cachedIn(viewModelScope)

    private var userName: String? = null
    fun setUserName(username: String) {
        userName = username
    }

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    private val _detailsError = MutableLiveData<String>()
    val detailsError: LiveData<String> = _detailsError

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            val response = repository.getUserDetails(username)
            when(response) {
                is Success -> {
                    _userDetails.postValue(response.body.asDomainModel())
                }
                is ApiError -> {
                    _detailsError.postValue(AppConstants.API_ERROR_MESSAGE)
                }
                is NetworkError -> {
                    _detailsError.postValue(AppConstants.NETWORK_ERROR_MESSAGE)
                }
                is UnknownError -> {
                    _detailsError.postValue(AppConstants.UNKNOWN_ERROR_MESSAGE)
                }
            }
        }
    }
}