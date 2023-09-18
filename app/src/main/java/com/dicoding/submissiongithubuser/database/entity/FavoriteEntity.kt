package com.dicoding.submissiongithubuser.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize

data class FavoriteEntity(
    @ColumnInfo(name = "username")
    var username: String? = null,
    @ColumnInfo(name = "avatarurl")
    var avatarUrl: String? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
) : Parcelable
