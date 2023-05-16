package com.lacourt.githubusers.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.lacourt.githubusers.MainCoroutineRule
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse
import com.lacourt.githubusers.network.dto.UserListedDTO
import com.lacourt.githubusers.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class PagingSourceTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: Repository
    private lateinit var userListPageSource: UserListPageSource

    @Mock
    private var service = Mockito.mock(GithubApiService::class.java)

    @Before
    fun setUp() {
        repository = Repository(service)
        userListPageSource = UserListPageSource(repository)
    }

    @Test
    fun `when load`() = runTest{
        //given
        val expectedResult =
            PagingSource.LoadResult.Page(
                data = listOf<UserListed>(),
                prevKey = null,
                nextKey = null
            )

        Mockito.`when`(repository.getUserList(0, 25))
            .thenReturn(NetworkResponse.Success(listOf<UserListedDTO>()))

        //then
        Assert.assertEquals(
            expectedResult,
            userListPageSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}