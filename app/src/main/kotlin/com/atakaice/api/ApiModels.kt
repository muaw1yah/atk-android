package com.atakaice.api

import java.util.*

class NewsResponse(val data: DataResponse)

class DataResponse(
    val children: List<ChildrenResponse>,
    val before: String?,
    val after: String?
)


class ChildrenResponse(
    val author: String,
    val title: String,
    val description: String,
    val pubDate: String,
    val files: List<String>,
    val link: String,
    val category: String
)

class NewsDataResponse(
    val author: String,
    val title: String,
    val description: String,
    val pubDate: Date,
    val files: List<String>,
    val link: String,
    val category: String
)


