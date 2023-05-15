package com.lacourt.githubusers.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lacourt.githubusers.databinding.ActivitySearchBinding
import com.lacourt.githubusers.model.UserListed
import com.lacourt.githubusers.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(), SearchAdapter.OnUserClick {

    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter by lazy { SearchAdapter(this, ArrayList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView(binding)
        attachSearchObserver()
        attachErrorObserver(binding)
        attachVisibilityObserver(binding)
        showKeyBoard(binding)
        searchClick(binding)
    }

    private fun searchClick(binding: ActivitySearchBinding) {
        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            return@setOnEditorActionListener when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.searchMovie(textView.text.toString())
                    true
                }
                else -> false
            }
        }
        binding.ivSearchIcon.setOnClickListener {
            viewModel.searchMovie(binding.etSearch.text.toString())
        }
    }

    private fun attachSearchObserver() {
        viewModel.searchResult.observe(this) { resultList ->
            searchAdapter.setList(resultList)
        }
    }

    private fun attachVisibilityObserver(binding: ActivitySearchBinding) {
        viewModel.noResultsVisibility.observe(this) { isVisible ->
            binding.tvNoResults.isVisible = isVisible
        }
        viewModel.errorVisibility.observe(this) { isVisible ->
            binding.tvError.isVisible = isVisible
        }
        viewModel.searchProgressBarVisibility.observe(this) { isVisible ->
            binding.pbSearch.isVisible = isVisible
        }
    }

    private fun attachErrorObserver(binding: ActivitySearchBinding) {
        viewModel.errorMessage.observe(this, Observer { error ->
            binding.tvError.text = error
        })
    }

    private fun setupRecyclerView(binding: ActivitySearchBinding) {
        binding.rvSearchedList.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }

    private fun showKeyBoard(binding: ActivitySearchBinding) {
        binding.etSearch.requestFocus()
        val imgr = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onClick(user: UserListed) {
        startActivity(UserDetailsActivity.newIntent(this, user.login))
    }

    companion object {
        fun newIntent(activity: Activity): Intent {
            return Intent(activity, SearchActivity::class.java)
        }
    }

}