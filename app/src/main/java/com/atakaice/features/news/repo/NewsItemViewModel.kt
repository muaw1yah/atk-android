package com.atakaice.features.news.repo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.atakaice.commons.ApplicationDatabase
import com.atakaice.commons.NewsItem
import kotlinx.coroutines.launch

class NewsItemViewModel (application: Application): AndroidViewModel(application) {

    private val repo : NewsItemRepository

    val allNewsItems: LiveData<List<NewsItem>>

    init {
        val newsItemDao = ApplicationDatabase.getDatabase(application).newsItemDao()
        repo = NewsItemRepository(newsItemDao)
        allNewsItems = repo.allNews
    }

    fun insertAll(items: List<NewsItem>) = viewModelScope.launch {
        repo.insertAll(items)
    }
}