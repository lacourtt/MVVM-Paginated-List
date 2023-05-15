package com.lacourt.githubusers.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.MainCoroutineRule
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse
import com.lacourt.githubusers.network.RequestError
import com.lacourt.githubusers.network.dto.UserDetailsDTO
import com.lacourt.githubusers.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.io.IOException
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var viewModel: MainViewModel? = null
    private var repository: Repository? = null

    @Mock
    private var service = Mockito.mock(GithubApiService::class.java)

    private lateinit var userDetailsDTO: UserDetailsDTO

    @Before
    fun setUp() {
        repository = Repository(service)
        viewModel = MainViewModel(repository!!)
        userDetailsDTO = UserDetailsDTO(
            "",1,"","","","","","","","","",
            "","","","","","",false,"","",
            "", "","",true,"","",1,1,1,1,
            "", "")
    }

    @After
    fun tearDown(){
        repository = null
        viewModel = null
    }

    @Test
    fun `when getUserDetails is called, then it should return the user details`() = runTest {
        Mockito.`when`(service.getUserDetails("username"))
            .thenReturn(NetworkResponse.Success(userDetailsDTO))

        viewModel?.getUserDetails("username")

        assertEquals(viewModel?.userDetails?.value, userDetailsDTO.asDomainModel())
    }

    @Test
    fun `when getUserDetails is called and receives an error from the service, then it should return the ApiError`() = runTest {
        Mockito.`when`(service.getUserDetails("username"))
            .thenReturn(TestUtil.apiError)

        viewModel?.getUserDetails("username")

        assertEquals(viewModel?.detailsError?.value, AppConstants.API_ERROR_MESSAGE)
    }

    @Test
    fun `when getUserDetails is called but operation fails or is interrupted, then it should return the NetworkError`() = runTest {
        Mockito.`when`(service.getUserDetails("username"))
            .thenReturn(TestUtil.networkError)

        viewModel?.getUserDetails("username")

        assertEquals(viewModel?.detailsError?.value, AppConstants.NETWORK_ERROR_MESSAGE)
    }

    @Test
    fun `when getUserDetails is called but something happened before the server get the request, then it should return the UnknownError`() = runTest {
        Mockito.`when`(service.getUserDetails("username"))
            .thenReturn(TestUtil.unknownError)

        viewModel?.getUserDetails("username")

        assertEquals(viewModel?.detailsError?.value, AppConstants.UNKNOWN_ERROR_MESSAGE)
    }
}