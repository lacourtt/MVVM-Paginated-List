package com.lacourt.githubusers.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lacourt.githubusers.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {

    private lateinit var binding: LoadMoreBinding

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        binding = LoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    inner class ViewHolder(retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btLoadMoreRetry.setOnClickListener { retry() }
        }

        fun setData(state: LoadState) {
            binding.apply {
                pbLoadMore.isVisible = state is LoadState.Loading
                tvLoadMore.isVisible = state is LoadState.Error
                btLoadMoreRetry.isVisible = state is LoadState.Error
                if (state is LoadState.Error) tvLoadMore.text = state.error.message
            }
        }
    }
}