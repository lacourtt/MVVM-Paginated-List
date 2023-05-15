package com.lacourt.githubusers.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lacourt.githubusers.MainCoroutineRule
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserDetails
import com.lacourt.githubusers.network.GithubApiService
import com.lacourt.githubusers.network.NetworkResponse
import com.lacourt.githubusers.network.dto.UserDetailsDTO
import com.lacourt.githubusers.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var viewModel: MainViewModel? = null

    @Mock
    private var repository = Mockito.mock(Repository::class.java)

    @Mock
    private var service = Mockito.mock(GithubApiService::class.java)

    private lateinit var userDetailsDTO: UserDetailsDTO

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
        userDetailsDTO = UserDetailsDTO(
            "",1,"","","","","","","","","",
            "","","","","","",false,"","",
            "", "","",true,"","",1,1,1,1,
            "", "")
    }

    @After
    fun tearDown(){
        viewModel = null
    }

    @Test
    fun `when getUserDetails is called, then it should return the user details`() = runTest {
        Mockito.`when`(service.getUserDetails("username"))
            .thenReturn(NetworkResponse.Success(userDetailsDTO))

        viewModel?.getUserDetails("username")

        assertEquals(viewModel?.userDetails?.value, userDetailsDTO.asDomainModel())
    }
}