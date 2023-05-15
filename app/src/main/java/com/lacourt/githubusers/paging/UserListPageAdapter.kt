package com.lacourt.githubusers.paging

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lacourt.githubusers.databinding.UserListItemBinding
import com.lacourt.githubusers.model.UserListed
import com.squareup.picasso.Picasso

class UserListPageAdapter(private val onClick: OnItemClickListener) : PagingDataAdapter<UserListed, UserListPageAdapter.UserListViewHolder>(DIFF_USER_CALLBACK) {

    private lateinit var binding: UserListItemBinding

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = UserListItemBinding.inflate(inflater, parent, false)
        return UserListViewHolder()
    }

    inner class UserListViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserListed) {
            binding.apply {
                tvUserName.text = user.login
                root.setOnClickListener {
                    onClick.onItemClick(user)
                }
                Picasso.get()
                    .load(user.avatar_url)
                    .fit()
                    .config(Bitmap.Config.RGB_565)
                    .into(ivUserAvatar)
            }
        }
    }

    companion object {
        private val DIFF_USER_CALLBACK = object : DiffUtil.ItemCallback<UserListed>() {
            override fun areItemsTheSame(oldItem: UserListed, newItem: UserListed): Boolean =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: UserListed, newItem: UserListed): Boolean =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(user: UserListed)
    }
}


