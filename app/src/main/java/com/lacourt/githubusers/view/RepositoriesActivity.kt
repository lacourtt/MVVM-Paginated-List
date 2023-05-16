package com.lacourt.githubusers.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.lacourt.githubusers.databinding.ActivityRepositoriesBinding
import com.lacourt.githubusers.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesActivity : AppCompatActivity() {
    private val repositoryAdapter by lazy { RepositoryAdapter() }
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setUserName(intent.getStringExtra("login")!!)

        setupRecyclerView(binding)

        lifecycleScope.launchWhenCreated {
            viewModel.repositoryList.collect {
                repositoryAdapter.submitData(it)
            }
        }

        binding.apply {
            lifecycleScope.launchWhenCreated {
                repositoryAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    pbRepository.isVisible = state is LoadState.Loading
                }
            }

            rvPublicRepos.adapter = repositoryAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    repositoryAdapter.retry()
                }
            )
        }
    }

    private fun setupRecyclerView(binding: ActivityRepositoriesBinding) {
        binding.apply {
            rvPublicRepos.adapter = repositoryAdapter
            rvPublicRepos.layoutManager = LinearLayoutManager(this@RepositoriesActivity)
        }
    }

    companion object {
        fun newIntent(activity: Activity, total: Int?, login: String): Intent? {
            if (total == null) return null
            val intent = Intent(activity, RepositoriesActivity::class.java)
            intent.putExtra("total", total)
            intent.putExtra("login", login)
            return intent
        }
    }

}