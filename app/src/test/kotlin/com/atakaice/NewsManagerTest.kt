package com.atakaice

import com.atakaice.api.*
import com.atakaice.commons.News
import com.atakaice.features.news.NewsManager
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response
import rx.observers.TestSubscriber
import java.util.*

class NewsManagerTest {

    var testSub = TestSubscriber<News>()
    var apiMock = mock<RestAPI>()
    var callMock = mock<Call<NewsResponse>>()

    @Before
    fun setUp() {
        testSub = TestSubscriber()
        apiMock = mock()
        callMock = mock()
        `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
    }

    @Test
    fun testSuccess_basic() {
        // prepare
        val newsResponse = NewsResponse(DataResponse(listOf(), null,  null))
        val response = Response.success(newsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // assert
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertCompleted()
    }

    @Test
    fun testSuccess_checkOneNews() {

        // prepare
        val newsData = ChildrenResponse(
            "author",
            "title",
            "description",
            "3435354",
            arrayOf("file1", "file2").toList(),
            "link",
            "category"
        )

        val newsResponse = NewsResponse(DataResponse(listOf(newsData), null, null))
        val response = Response.success(newsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // assert
        testSub.assertNoErrors()
        testSub.assertValueCount(1)
        testSub.assertCompleted()

        assert(testSub.onNextEvents[0].news[0].author == newsData.author)
        assert(testSub.onNextEvents[0].news[0].title == newsData.title)
    }

    @Test
    fun testError() {
        // prepare
        val responseError = Response.error<NewsResponse>(500,
            ResponseBody.create(MediaType.parse("application/json"), ""))

        `when`(callMock.execute()).thenReturn(responseError)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testSub)

        // assert
        assert(testSub.onErrorEvents.size == 1)
    }
}

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)