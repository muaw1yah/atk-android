package com.atakaice.features.news_cards

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.InfiniteScrollListener
import com.atakaice.commons.News
import com.atakaice.commons.RxBaseFragment
import com.atakaice.commons.extensions.inflate
import com.atakaice.features.news.NewsManager
import kotlinx.android.synthetic.main.fragment_news.*
import rx.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news_cards.*


class NewsCardsFragment : RxBaseFragment() {

    companion object {
        private val KEY_NEWS = "news"
    }

    private var apiNews: News? = null
    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news_cards)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipeStack.apply {
            var linearLayout = LinearLayoutManager(context)
        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_NEWS)) {
            apiNews = savedInstanceState.get(KEY_NEWS) as News
            //(news_list as NewsCardsAdapter).clearAndAddNews(apiNews!!.news)
            swipeStack.adapter = SwipeStackAdapter(context, apiNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (swipeStack.adapter as SwipeStackAdapter).data
        if(apiNews != null && news.isNotEmpty()) {
            outState.putParcelable(KEY_NEWS, apiNews?.copy(news = news))
        }
    }

    private fun requestNews() {
        // (news_list.adapter as NewsAdapter).addNews(news)
        val subscription = newsManager.getNews(apiNews?.after ?: "", "10")
            .subscribeOn(Schedulers.io())
            .subscribe(
                {retrievedNews ->
                    apiNews = retrievedNews
                    //(news_list.adapter as NewsCardsAdapter).addNews(retrievedNews.news)
                    swipeStack.adapter = SwipeStackAdapter(context, retrievedNews.news)
                },
                {e -> Snackbar.make(swipeStack, e.message ?: "", Snackbar.LENGTH_LONG).show()
                Log.e("CONNECTION", e.message ?: "")}
            )

        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (swipeStack.adapter == null) {
            swipeStack.adapter = SwipeStackAdapter(context)
        }
    }
}