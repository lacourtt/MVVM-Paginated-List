package com.lacourt.githubusers.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserRepository
import com.lacourt.githubusers.network.NetworkResponse
import com.lacourt.githubusers.repository.Repository

class RepoListPageSource(private val repository: Repository, private val username: String)
    : PagingSource<Int, UserRepository>() {

    override fun getRefreshKey(state: PagingState<Int, UserRepository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepository> {
        var pageNumber = params.key ?: 1
        val response = repository.getUserRepoList(username, pageNumber)
        return when(response) {
            is NetworkResponse.Success -> {
                val repos = response.body.asDomainModel()
                LoadResult.Page(
                    data = repos,
                    prevKey = null,
                    nextKey = pageNumber + 1
                )
            }
            is NetworkResponse.ApiError -> LoadResult.Error(Exception(AppConstants.API_ERROR_MESSAGE))
            is NetworkResponse.NetworkError -> LoadResult.Error(Exception(AppConstants.NETWORK_ERROR_MESSAGE))
            is NetworkResponse.UnknownError -> LoadResult.Error(Exception(AppConstants.UNKNOWN_ERROR_MESSAGE))
        }
    }
}