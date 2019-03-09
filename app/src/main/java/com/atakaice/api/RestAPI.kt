package com.atakaice.api

import android.util.Log
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI() {

    private val newsApi: NewsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://atakaice.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        newsApi = retrofit.create(NewsApi::class.java)
    }

    fun getNews(after: String, limit: String): Call<NewsResponse> {
        return newsApi.getTop(after, limit)
    }
}