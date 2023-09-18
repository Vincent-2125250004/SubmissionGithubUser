package com.dicoding.submissiongithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity
import com.dicoding.submissiongithubuser.database.room.FavoriteDao
import com.dicoding.submissiongithubuser.database.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteEntity>> = mFavoriteDao.getAllFavorite()

    fun insert(username: FavoriteEntity) {
        executorService.execute { mFavoriteDao.insert(username) }
    }

    fun delete(username: String) {
        executorService.execute { mFavoriteDao.deleteAll(username) }
    }

    fun selectUser(username: String): LiveData<Boolean> {
        return mFavoriteDao.getFavoriteUserByUsername(username).map {
            it.isNotEmpty()
        }
    }

}