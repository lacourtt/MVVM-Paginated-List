package com.lacourt.githubusers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserDetails
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.paging.UserListPageSource
import com.lacourt.githubusers.repository.Repository
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError
import kotlinx.coroutines.launch

class MainViewModel(private val service: GithubApiService, private val repository: Repository): ViewModel() {

    init {
        Log.d("igor", "MainViewModel: init")
    }

    val userList = Pager(PagingConfig(pageSize = 25, initialLoadSize = 25)) {
        UserListPageSource(service)
    }.flow.cachedIn(viewModelScope)

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
                    _detailsError.postValue(response.body.message.toString())
                }
                is NetworkError -> {
                    _detailsError.postValue(response.error.localizedMessage)
                }
                is UnknownError -> {
                    _detailsError.postValue(response.error?.localizedMessage)
                }
            }
        }
    }
}