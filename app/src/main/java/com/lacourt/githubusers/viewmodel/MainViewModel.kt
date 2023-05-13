package com.lacourt.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lacourt.githubusers.repository.Repository
import com.lacourt.githubusers.network.dto.UserListed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    private val _usersList = MutableLiveData<List<UserListed>>()
    val usersList: LiveData<List<UserListed>> = _usersList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchUsersList()
        }
    }

    private suspend fun fetchUsersList() {
        _usersList.postValue(repository.fetchUsersList())
    }
}