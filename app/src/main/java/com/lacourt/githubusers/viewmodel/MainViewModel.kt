package com.lacourt.githubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lacourt.githubusers.repository.Repository
import com.lacourt.githubusers.network.dto.UserListedDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    private val _usersList = MutableLiveData<List<UserListedDTO>>()
    val usersList: LiveData<List<UserListedDTO>> = _usersList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchUsersList()
        }
    }

    private suspend fun fetchUsersList() {
        _usersList.postValue(repository.fetchUsersList())
    }
}