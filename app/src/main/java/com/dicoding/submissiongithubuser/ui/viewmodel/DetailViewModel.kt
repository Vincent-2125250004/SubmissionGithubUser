package com.dicoding.submissiongithubuser.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissiongithubuser.api.response.DetailUserResponse
import com.dicoding.submissiongithubuser.api.retrofit.ApiConfig
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity
import com.dicoding.submissiongithubuser.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUsername = MutableLiveData<DetailUserResponse>()
    val detailUsername: LiveData<DetailUserResponse> = _detailUsername

    fun insert(favoriteEntity: FavoriteEntity) {
        mFavoriteRepository.insert(favoriteEntity)
    }

    fun selectUser(login: String): LiveData<Boolean> {
        return mFavoriteRepository.selectUser(login)
    }

    fun delete(username: String) {
        mFavoriteRepository.delete(username)
    }


    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUsername.value = response.body()
                    Log.d(TAG, "${response.body()}")
                } else {
                    Log.e(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}