package com.lacourt.githubusers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lacourt.githubusers.databinding.ActivityMainBinding
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.paging.UserListPageAdapter
import com.lacourt.githubusers.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), UserListPageAdapter.OnItemClickListener {
    private val viewModel: MainViewModel by viewModel()

    private val userListAdapter by lazy { UserListPageAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userList.observe(this) {
            userListAdapter.submitData(lifecycle, it)
        }

        binding.apply {
            rvUsersList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = userListAdapter
            }


            // change to Lifecycle.repeatOnLifecycle
            lifecycleScope.launch {
                userListAdapter.loadStateFlow.collect{ state ->
                    val isEmpty = userListAdapter.itemCount == 0

                    // Show empty list
                    llEmptyList.isVisible = isEmpty

                    pbUserList.isVisible = state.refresh is LoadState.Loading
                }
            }

            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            rvUsersList.addItemDecoration(decoration)
            rvUsersList.adapter = userListAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    userListAdapter.retry()
                }
            )

            tvSearchUsers.setOnClickListener {
                startActivity(SearchActivity.newIntent(this@MainActivity))
            }

            btRetry.setOnClickListener {
                userListAdapter.retry()
            }
        }
    }

    override fun onItemClick(user: UserListed) {
        startActivity(UserDetailsActivity.newIntent(this, user.login))
    }
}