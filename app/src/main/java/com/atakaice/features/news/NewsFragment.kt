package com.atakaice.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.atakaice.R
import com.atakaice.commons.InfiniteScrollListener
import com.atakaice.commons.News
import com.atakaice.commons.RxBaseFragment
import com.atakaice.features.news.adapter.NewsAdapter
import com.atakaice.commons.extensions.inflate
import kotlinx.android.synthetic.main.fragment_news.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewsFragment : RxBaseFragment() {

    private var news: News? = null
    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.setHasFixedSize(true)
        var linearLayout = LinearLayoutManager(context)
        news_list.layoutManager = linearLayout
        news_list.clearOnScrollListeners()
        news_list.addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))

        initAdapter()

        if (savedInstanceState == null) {
            requestNews()
        }
    }

    private fun requestNews() {
        // (news_list.adapter as NewsAdapter).addNews(news)
        val subscription = newsManager.getNews(news?.after ?: "", "10")
            .subscribeOn(Schedulers.io())
            .subscribe(
                {retrievedNews ->
                    news = retrievedNews
                    (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)},
                {e -> Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                Log.e("CONNECTION", e.message ?: "")}
            )

        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }
}