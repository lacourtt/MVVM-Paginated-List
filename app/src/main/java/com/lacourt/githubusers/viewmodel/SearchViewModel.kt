package com.lacourt.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lacourt.githubusers.AppConstants
import com.lacourt.githubusers.mapping.asDomainModel
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.network.NetworkResponse.Success
import com.lacourt.githubusers.network.NetworkResponse.NetworkError
import com.lacourt.githubusers.network.NetworkResponse.ApiError
import com.lacourt.githubusers.network.NetworkResponse.UnknownError
import com.lacourt.githubusers.repository.Repository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository): ViewModel() {
    private var _searchResult = MutableLiveData<List<UserListed>>()
    var searchResult: LiveData<List<UserListed>> = _searchResult

    private var _searchProgressBarVisibility = MutableLiveData(false)
    var searchProgressBarVisibility: LiveData<Boolean> = _searchProgressBarVisibility

    private var _errorVisibility = MutableLiveData(false)
    var errorVisibility: LiveData<Boolean> = _errorVisibility

    private var _noResultsVisibility = MutableLiveData(true)
    var noResultsVisibility: LiveData<Boolean> = _noResultsVisibility

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    fun searchUser(username: String){
        showSearchProgressBar(true)
        showError(false)
        try {
            viewModelScope.launch {
                val response = repository.searchUser(username)
                showSearchProgressBar(false)
                when (response) {
                    is Success -> {
                        val results = response.body.items.asDomainModel()
                        _searchResult.value = results
                        showResults(results)
                    }
                    is ApiError -> {
                        _errorMessage.value = AppConstants.API_ERROR_MESSAGE
                        showErrorScreen()
                    }
                    is NetworkError -> {
                        _errorMessage.value = AppConstants.NETWORK_ERROR_MESSAGE
                        showErrorScreen()
                    }

                    is UnknownError -> {
                        _errorMessage.value = AppConstants.UNKNOWN_ERROR_MESSAGE
                        showErrorScreen()
                    }
                }
            }
        } catch (exception: Exception){
            throw exception
        }
    }

    private fun showResults(results: List<UserListed>) {
        if (results.isNullOrEmpty()){
            showNoResultsMessage(true)
        } else {
            showNoResultsMessage(false)
        }
    }

    private fun showErrorScreen() {
        showError(true)
        showNoResultsMessage(false)
    }

    private fun showError(shouldShow: Boolean) {
        _errorVisibility.value = shouldShow
    }

    private fun showSearchProgressBar(shouldShow: Boolean) {
        _searchProgressBarVisibility.value = shouldShow
    }

    private fun showNoResultsMessage(shouldShow: Boolean) {
        _noResultsVisibility.value = shouldShow
    }

}