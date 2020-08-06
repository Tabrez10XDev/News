package com.example.news.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface articleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(Article: article) : Long

    @Query("SELECT * FROM articles")
    fun getAll():LiveData<List<article>>
}