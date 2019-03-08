package com.atakaice.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/api")
    fun getTop(@Query("cursor") after: String,
               @Query("limit") limit: String): Call<NewsResponse>
}