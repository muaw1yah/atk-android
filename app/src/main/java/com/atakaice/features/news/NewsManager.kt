package com.atakaice.features.news

import android.util.Log
import com.atakaice.api.RestAPI
import com.atakaice.commons.News
import com.atakaice.commons.NewsItem
import rx.Observable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/*
    News Manager allows you to request more news
 */
class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(after: String, limit: String = "10"): Observable<News> {
        return Observable.create {
                subscriber ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body().data
                val newsRes = dataResponse.children.map {
                    val item = it
                    val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(item.pubDate)
                    NewsItem(
                        item.author, item.title, timestamp.time, item.link, item.description,
                        item.category, item.files
                    )
                }

                val news = News(
                    dataResponse.after ?: "",
                    dataResponse.before ?: "",
                    newsRes)

                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}