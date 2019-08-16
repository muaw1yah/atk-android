package com.atakaice.features.news

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.InfiniteScrollListener
import com.atakaice.commons.News
import com.atakaice.commons.RxBaseFragment
import com.atakaice.features.news.adapter.NewsAdapter
import com.atakaice.commons.extensions.inflate
import kotlinx.android.synthetic.main.fragment_news.*
import rx.schedulers.Schedulers

class NewsFragment : RxBaseFragment() {

    companion object {
        private val KEY_NEWS = "news"
    }

    private var apiNews: News? = null
    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_news)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        news_list.apply {
            setHasFixedSize(true)
            var linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_NEWS)) {
            apiNews = savedInstanceState.get(KEY_NEWS) as News
            (news_list as NewsAdapter).clearAndAddNews(apiNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (news_list.adapter as NewsAdapter).getNews()
        if(apiNews != null && news.isNotEmpty()) {
            outState.putParcelable(KEY_NEWS, apiNews?.copy(news = news))
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews(apiNews?.after ?: "", "10")
            .subscribeOn(Schedulers.io())
            .subscribe(
                {retrievedNews ->
                    apiNews = retrievedNews
                    (news_list.adapter as NewsAdapter).addNews(retrievedNews.news)},
                {e -> Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                Log.e("CONNECTION 81", e.message ?: "")}
            )

        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (news_list.adapter == null) {
            news_list.adapter = NewsAdapter()
        }
    }
}