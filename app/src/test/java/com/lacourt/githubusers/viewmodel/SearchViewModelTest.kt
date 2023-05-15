package com.lacourt.githubusers.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.MainCoroutineRule
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse
import com.lacourt.githubusers.network.dto.SearchResultDTO
import com.lacourt.githubusers.network.dto.UserListedDTO
import com.lacourt.githubusers.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var viewModel: SearchViewModel? = null
    private var repository: Repository? = null

    @Mock
    private var service = Mockito.mock(GithubApiService::class.java)

    private val searchResultDTO = SearchResultDTO(
        1,
        false,
        listOf(
            UserListedDTO(
                "",1,"","","","","",
                "","","","","","","",
                "","","",false,0.0
            )
        )
    )

    @Before
    fun setUp() {
        repository = Repository(service)
        viewModel = SearchViewModel(repository!!)
    }

    @After
    fun tearDown(){
        repository = null
        viewModel = null
    }

    @Test
    fun `when searchUser is called, then it should return the user details`() = runTest {
        Mockito.`when`(service.searchUser("username"))
            .thenReturn(NetworkResponse.Success(searchResultDTO))

        viewModel?.searchUser("username")

        assertEquals(
            searchResultDTO.items.asDomainModel(),
            viewModel?.searchResult?.value
        )
    }

    @Test
    fun `when searchUser is called and receives an error from the service, then it should return the ApiError`() = runTest {
        Mockito.`when`(service.searchUser("username"))
            .thenReturn(TestUtil.apiError)

        viewModel?.searchUser("username")

        assertEquals(viewModel?.errorVisibility?.value, true)
        assertEquals(viewModel?.errorMessage?.value, AppConstants.API_ERROR_MESSAGE)
    }

    @Test
    fun `when searchUser is called but operation fails or is interrupted, then it should return the NetworkError`() = runTest {
        Mockito.`when`(service.searchUser("username"))
            .thenReturn(TestUtil.networkError)

        viewModel?.searchUser("username")

        assertEquals(viewModel?.errorVisibility?.value, true)
        assertEquals(viewModel?.errorMessage?.value, AppConstants.NETWORK_ERROR_MESSAGE)
    }

    @Test
    fun `when searchUser is called but something happened before the server get the request, then it should return the UnknownError`() = runTest {
        Mockito.`when`(service.searchUser("username"))
            .thenReturn(TestUtil.unknownError)

        viewModel?.searchUser("username")

        assertEquals(viewModel?.errorVisibility?.value, true)
        assertEquals(viewModel?.errorMessage?.value, AppConstants.UNKNOWN_ERROR_MESSAGE)
    }
}