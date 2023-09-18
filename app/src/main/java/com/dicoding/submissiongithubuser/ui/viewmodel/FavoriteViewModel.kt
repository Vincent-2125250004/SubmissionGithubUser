package com.dicoding.submissiongithubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity
import com.dicoding.submissiongithubuser.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> {
        _isLoading.value = false
        return mFavoriteRepository.getAllUsers()
    }

}