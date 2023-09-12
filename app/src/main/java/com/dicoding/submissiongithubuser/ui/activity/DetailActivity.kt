package com.dicoding.submissiongithubuser.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.submissiongithubuser.R
import com.dicoding.submissiongithubuser.adapter.SectionPagerAdapter
import com.dicoding.submissiongithubuser.api.response.DetailUserResponse
import com.dicoding.submissiongithubuser.api.response.ItemsItem
import com.dicoding.submissiongithubuser.databinding.ActivityDetailBinding
import com.dicoding.submissiongithubuser.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tabfollowers,
            R.string.tabfollowing
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<ItemsItem>("varUsername")

        user?.let { itemsItem ->
            viewModel.getUserDetail(itemsItem.login)


            viewModel.detailUsername.observe(this) {
                showUserDetail(it)
            }
            viewModel.isLoading.observe(this) {
                showLoading(it)
            }

        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = user?.login.toString()
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showUserDetail(detailResponse: DetailUserResponse) {
        binding.apply {
            tvDetailName.text = detailResponse.name
            tvLoginName.text = detailResponse.login
            tvFollowers.text = detailResponse.followers.toString()
            tvFollowing.text = detailResponse.following.toString()
            Glide.with(civDetailProfile).load(detailResponse.avatarUrl).into(civDetailProfile)
        }
    }
}