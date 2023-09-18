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
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity
import com.dicoding.submissiongithubuser.databinding.ActivityDetailBinding
import com.dicoding.submissiongithubuser.ui.viewmodel.DetailViewModel
import com.dicoding.submissiongithubuser.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var userFav: FavoriteEntity? = FavoriteEntity()
    private var isFav = false

    private lateinit var username: String
    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(PAKET_USERNAME).toString()
        avatar = intent.getStringExtra(PAKET_AVATAR).toString()

        viewModel.getUserDetail(username)

        viewModel.detailUsername.observe(this) {
            showUserDetail(it)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }



        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.selectUser(username).observe(this) {
            if (it) {
                binding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_24))
                isFav = true
            } else {
                binding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_24))
                isFav = false
            }
        }

        binding.fabFavorite.setOnClickListener {
            if (isFav) {
                viewModel.delete(username)
                isFav = false
            } else {
                userFav?.username = username
                userFav?.avatarUrl = avatar
                viewModel.insert(userFav as FavoriteEntity)
                isFav = true
            }
        }


        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    companion object {

        const val PAKET_USERNAME = "USERNAME"
        const val PAKET_AVATAR = "AVATAR"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tabfollowers,
            R.string.tabfollowing
        )
    }
}