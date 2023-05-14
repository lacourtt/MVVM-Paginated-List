package com.lacourt.githubusers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError

class UserListPageSource(private val service: GithubApiService) : PagingSource<Int, UserListed>(){
    private val STARTING_KEY = 0

    override fun getRefreshKey(state: PagingState<Int, UserListed>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserListed> {
        // Start paging with the STARTING_KEY if this is the first load
        val start = params.key ?: STARTING_KEY

        val response = service.getUserList(start, params.loadSize)
        return when(response) {
            is Success -> {
                val users = response.body.asDomainModel()
                LoadResult.Page(
                    data = users,
                    prevKey = null, // Only paging forward.
                    nextKey = users.last().lastId
                )
            }
            is ApiError -> LoadResult.Error(Exception(response.body.message))
            is NetworkError -> LoadResult.Error(java.lang.Exception("Network error"))
            is UnknownError -> LoadResult.Error(java.lang.Exception("Unknown error"))
        }
    }
}