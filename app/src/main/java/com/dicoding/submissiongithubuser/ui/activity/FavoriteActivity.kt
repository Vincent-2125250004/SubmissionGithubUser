package com.dicoding.submissiongithubuser.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubuser.adapter.FavoritedAdapter
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity
import com.dicoding.submissiongithubuser.databinding.ActivityFavoriteBinding
import com.dicoding.submissiongithubuser.ui.viewmodel.FavoriteViewModel
import com.dicoding.submissiongithubuser.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favViewModel by viewModels<FavoriteViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val adapter = FavoritedAdapter()
        binding.rvFavorite.adapter = adapter

        favViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favViewModel.getAllFavorites().observe(this) { fav ->
            val items = arrayListOf<FavoriteEntity>()
            fav.map {
                val item = FavoriteEntity(it.username, it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}