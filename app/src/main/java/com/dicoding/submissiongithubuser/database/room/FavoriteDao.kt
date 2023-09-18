package com.dicoding.submissiongithubuser.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.submissiongithubuser.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(username: FavoriteEntity)

    @Query("SELECT * from users ORDER BY username ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM users WHERE username = :username")
    fun deleteAll(username: String)

    @Query("SELECT * FROM users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<List<FavoriteEntity>>

}