package com.lacourt.githubusers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lacourt.githubusers.R
import com.lacourt.githubusers.databinding.ActivityMainBinding
import com.lacourt.githubusers.paging.UserListPageAdapter
import com.lacourt.githubusers.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    private val userListAdapter by inject<UserListPageAdapter>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            viewModel.userList.collect {
                userListAdapter.submitData(it)
            }
        }

        binding.apply {
            rvUsersList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = userListAdapter
            }

//            lifecycleScope.launchWhenCreated {
//                userListAdapter.loadStateFlow.collect{
//                    val state = it.refresh
//                    pbUserList.isVisible = state is LoadState.Loading
//                }
//            }

            rvUsersList.adapter = userListAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    userListAdapter.retry()
                }
            )
        }



    }
}