package com.dicoding.submissiongithubuser.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubuser.adapter.UserAdapter
import com.dicoding.submissiongithubuser.api.response.ItemsItem
import com.dicoding.submissiongithubuser.databinding.ActivityMainBinding
import com.dicoding.submissiongithubuser.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.text = searchView.text
                var query = searchBar.text.toString()
                searchView.hide()
                mainViewModel.getUsername(query)
//                searchBar.clearText()
                false

            }
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsername.layoutManager = layoutManager

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listUser.observe(this) {
            setUsername(it)
        }

    }


    private fun setUsername (username : List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(username)
        binding.rvUsername.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}