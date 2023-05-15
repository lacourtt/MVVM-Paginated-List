package com.lacourt.githubusers.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError
import com.lacourt.githubusers.repository.Repository

class UserListPageSource(private val repository: Repository) : PagingSource<Int, UserListed>(){

    override fun getRefreshKey(state: PagingState<Int, UserListed>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserListed> {
        val response = repository.getUserList(params.key ?: 0, params.loadSize)
        return when(response) {
            is Success -> {
                val users = response.body.asDomainModel()
                LoadResult.Page(
                    data = users,
                    prevKey = null, // Only paging forward.
                    nextKey = users.last().id
                )
            }
            is ApiError -> LoadResult.Error(Exception(AppConstants.API_ERROR_MESSAGE))
            is NetworkError -> LoadResult.Error(java.lang.Exception(AppConstants.NETWORK_ERROR_MESSAGE))
            is UnknownError -> LoadResult.Error(java.lang.Exception(AppConstants.UNKNOWN_ERROR_MESSAGE))
        }
    }
}