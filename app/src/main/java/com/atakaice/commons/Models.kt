package com.atakaice.commons

import com.atakaice.commons.adapter.AdapterConstants
import com.atakaice.commons.adapter.ViewType
import java.util.*


data class News(
    val after: String,
    val before: String,
    val news: List<NewsItem>
)

data class NewsItem(
    val author: String,
    val title: String,
//    val pubDate: Date,
    val link: String,
    val description: String,
    val category: String,
    val files: List<String>
) : ViewType {
    override fun getViewType() = AdapterConstants.NEWS
}