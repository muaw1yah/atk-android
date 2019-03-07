package com.atakaice.commons.models

import com.atakaice.commons.adapter.AdapterConstants
import com.atakaice.commons.adapter.ViewType

data class NewsItem(
    val author: String,
    val title: String,
    val created: Long,
    val thumbnail: String,
    val url: String,
    val description: String
) : ViewType {
    override fun getViewType() = AdapterConstants.NEWS
}