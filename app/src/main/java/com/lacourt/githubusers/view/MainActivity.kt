package com.lacourt.githubusers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lacourt.githubusers.R
import com.lacourt.githubusers.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.usersList.observe(this) { users ->
            val users = users
        }
    }
}