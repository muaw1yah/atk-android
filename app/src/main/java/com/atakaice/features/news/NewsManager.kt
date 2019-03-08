package com.atakaice.features.news

import com.atakaice.commons.models.NewsItem
import rx.Observable

/*
    News Manager allows you to request more news
 */
class NewsManager {
    fun getNews() : Observable<List<NewsItem>> {
        return Observable.create {
            subscriber ->

            var news = mutableListOf<NewsItem>()
            for(i in 1..10) {
                news.add(
                    NewsItem(
                        "author$i",
                        "Title$i",
                        1457207701L - i * 200,
                        "http://lorempixel.com/200/200/technics/$i",
                        "url",
                        "description$i"
                    ))
            }
            subscriber.onNext(news)
        }
    }
}