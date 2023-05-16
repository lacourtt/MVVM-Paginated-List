package com.lacourt.githubusers.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lacourt.githubusers.databinding.ActivityUserDetailsBinding
import com.lacourt.githubusers.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.userDetails.observe(this) {
            binding.apply {
                Picasso.get().load(it.avatar_url).into(ivPicture)
                tvName.text = it.name
                tvLocation.text = it.location
                tvCompany.text = it.company
                tvFollowers.append("\n${it.followers.toString()}")
                tvPublicRepos.append("\n${it.public_repos.toString()}")
            }
        }

        viewModel.detailsError.observe(this) {
            binding.tvDetailsError.visibility = View.VISIBLE
            binding.tvDetailsError.text = it
        }

        binding.tvPublicRepos.setOnClickListener {
            RepositoriesActivity.newIntent(
                this, viewModel.userDetails.value?.public_repos,
                intent.getStringExtra("login")!!)?.let{
                startActivity(it)
            }
        }

        viewModel.getUserDetails(intent.getStringExtra("login")!!)
    }

    companion object {
        fun newIntent(activity: Activity, login: String): Intent {
            val intent = Intent(activity, UserDetailsActivity::class.java)
            intent.putExtra("login", login)
            return intent
        }
    }
}