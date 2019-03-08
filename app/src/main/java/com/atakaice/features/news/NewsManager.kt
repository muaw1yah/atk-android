package com.atakaice.features.news

import android.util.Log
import com.atakaice.api.RestAPI
import com.atakaice.commons.models.NewsItem
import rx.Observable

/*
    News Manager allows you to request more news
 */
class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(limit: String = "10"): Observable<List<NewsItem>> {
        return Observable.create {
                subscriber ->
            val callResponse = api.getNews("", limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val news = response.body().data.children.map {
                    val item = it
                    NewsItem(item.author, item.title, item.link, item.description,
                        item.category, item.files)
                }
                subscriber.onNext(news)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}