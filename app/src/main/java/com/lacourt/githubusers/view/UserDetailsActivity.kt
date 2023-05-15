package com.lacourt.githubusers.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lacourt.githubusers.databinding.ActivityUserDetailsBinding

class UserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        fun newIntent(activity: Activity, login: String): Intent {
            val intent = Intent(activity, UserDetailsActivity::class.java)
            intent.putExtra("login", login)
            return intent
        }
    }
}