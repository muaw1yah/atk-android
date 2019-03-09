package com.atakaice.features.news

import android.util.Log
import com.atakaice.api.RestAPI
import com.atakaice.commons.News
import com.atakaice.commons.NewsItem
import rx.Observable

/*
    News Manager allows you to request more news
 */
class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(after: String, limit: String = "10"): Observable<News> {
        Log.i("NewsManager L14 AFTER", after)
        return Observable.create {
                subscriber ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body().data
                val newsRes = dataResponse.children.map {
                    val item = it
                    NewsItem(
                        item.author, item.title, item.link, item.description,
                        item.category, item.files
                    )
                }

                Log.i("NewsManager L31 AFTER", dataResponse.after)
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