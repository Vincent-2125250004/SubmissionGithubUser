package com.dicoding.submissiongithubuser.ui.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiongithubuser.R
import com.dicoding.submissiongithubuser.ui.viewmodel.SettingViewModel
import com.dicoding.submissiongithubuser.ui.viewmodel.SettingsViewModelFactory
import com.dicoding.submissiongithubuser.util.SettingPreferences
import com.dicoding.submissiongithubuser.util.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        ).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            Log.d(TAG, "Theme preference: $isDarkModeActive")
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
            Log.d(TAG, "Theme preference saved: $isChecked")
        }
    }
}