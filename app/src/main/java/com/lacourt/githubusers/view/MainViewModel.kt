package com.lacourt.githubusers.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lacourt.githubusers.Repository
import com.lacourt.githubusers.view.dto.UserList
import com.lacourt.githubusers.view.dto.UserListed

class MainViewModel: ViewModel() {
    private val usersList = MutableLiveData<UserList>()
    val _usersList: LiveData<UserList> = usersList

    fun fetchUsersList() {
        usersList.value = Repository().fetchUsersList()
    }
}