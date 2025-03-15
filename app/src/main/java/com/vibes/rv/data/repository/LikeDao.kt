package com.vibes.rv.data.repository

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.vibes.rv.data.model.Like

@Dao
interface LikeDao {
    @Upsert
    suspend fun create(like: Like)

    @Query("SELECT * FROM `Like`")
    suspend fun getAll(): List<Like>
}