package com.dicoding.submissiongithubuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubuser.R
import com.dicoding.submissiongithubuser.adapter.UserAdapter
import com.dicoding.submissiongithubuser.api.response.ItemsItem
import com.dicoding.submissiongithubuser.databinding.ActivityMainBinding
import com.dicoding.submissiongithubuser.ui.viewmodel.MainViewModel
import com.dicoding.submissiongithubuser.util.SettingPreferences
import com.dicoding.submissiongithubuser.util.dataStore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var settingPreferences: SettingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        settingPreferences = SettingPreferences.getInstance(dataStore)
        val pref = settingPreferences.getThemeSetting().asLiveData()
        pref.observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.text = searchView.text
                var query = searchBar.text.toString()
                searchView.hide()
                mainViewModel.getUsername(query)
                false
            }

            searchBar.inflateMenu(R.menu.searchbar_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_settings -> {
                        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.menu_favorite -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }

//        val settingsMenu = binding.searchBar.menu.findItem(R.id.menu_settings)
//        settingsMenu.setOnMenuItemClickListener {
//            val intent = Intent(this, SettingsActivity::class.java)
//            startActivity(intent)
//            true
//        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUsername.layoutManager = layoutManager

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listUser.observe(this) {
            setUsername(it)
        }

    }

    private fun setUsername(username: List<ItemsItem>) {
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