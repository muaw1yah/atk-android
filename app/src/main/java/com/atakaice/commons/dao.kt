package com.atakaice.commons

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsItemDao {

    @Query("SELECT * from news_item")
    fun getAllNews(): LiveData<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: NewsItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<NewsItem>)

    @Query("DELETE FROM news_item")
    suspend fun deleteAll()
}