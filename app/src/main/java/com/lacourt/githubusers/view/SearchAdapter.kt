package com.lacourt.githubusers.view

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lacourt.githubusers.databinding.UserListItemBinding
import com.lacourt.githubusers.model.UserListed
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val onUserClick: OnUserClick,
    private var list: ArrayList<UserListed>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: UserListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = UserListItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(list: List<UserListed>?) {
        this.list.clear()
        list?.let {
            this.list.addAll(it)
        }
        notifyDataSetChanged()
    }

    interface OnUserClick {
        fun onClick(user: UserListed)
    }

    inner class SearchViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserListed) {
            binding.apply {
                tvUserName.text = user.login
                root.setOnClickListener {
                    onUserClick.onClick(user)
                }
                Picasso.get()
                    .load(user.avatar_url)
                    .fit()
                    .config(Bitmap.Config.RGB_565)
                    .into(ivUserAvatar)
            }
        }
    }
}