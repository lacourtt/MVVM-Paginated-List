package com.lacourt.githubusers.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lacourt.githubusers.databinding.UserRepoListItemBinding
import com.lacourt.githubusers.model.UserRepository

class RepositoryAdapter : PagingDataAdapter<UserRepository, RepositoryAdapter.RepositoryViewHolder>(DIFF_REPO_CALLBACK){
    private lateinit var binding: UserRepoListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = UserRepoListItemBinding.inflate(inflater, parent, false)
        return RepositoryViewHolder()
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class RepositoryViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: UserRepository) {
            binding.apply {
                tvRepoName.text = repository.name
                tvUserRepoDescription.text = repository.description
            }
        }
    }

    companion object {
        private val DIFF_REPO_CALLBACK = object : DiffUtil.ItemCallback<UserRepository>() {
            override fun areItemsTheSame(oldItem: UserRepository, newItem: UserRepository): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: UserRepository, newItem: UserRepository): Boolean =
                oldItem == newItem
        }
    }
}