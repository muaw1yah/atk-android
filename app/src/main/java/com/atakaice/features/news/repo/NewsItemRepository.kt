package com.atakaice.features.news.repo

import androidx.lifecycle.LiveData
import com.atakaice.commons.NewsItem
import com.atakaice.commons.NewsItemDao

class NewsItemRepository (private val newsItemDao: NewsItemDao) {
    val allNews : LiveData<List<NewsItem>> = newsItemDao.getAllNews()

    suspend fun insert(items: NewsItem) {
        newsItemDao.insert(items)
    }

    suspend fun insertAll(items: List<NewsItem>) {
        newsItemDao.insertAll(items)
    }
}