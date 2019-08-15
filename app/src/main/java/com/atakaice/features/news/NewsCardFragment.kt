package com.atakaice.features.news

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.News
import com.atakaice.commons.RxBaseFragment
import com.atakaice.commons.ShuffleListener
import com.atakaice.commons.extensions.inflate
import com.atakaice.features.news.adapter.ShuffleAdapter
import kotlinx.android.synthetic.main.fragment_news_cards.*

import rx.schedulers.Schedulers

class NewsCardFragment : RxBaseFragment() {

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

        shuffle.apply {
            var linearLayout = LinearLayoutManager(context)
            clearAnimation()
            shuffleAdapter = ShuffleAdapter()
            addListener(ShuffleListener({ requestNews()}, linearLayout))
        }

//        news_list.apply {
//            setHasFixedSize(true)
//            var linearLayout = LinearLayoutManager(context)
//            layoutManager = linearLayout
//            clearOnScrollListeners()
//            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
//        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_NEWS)) {
            apiNews = savedInstanceState.get(KEY_NEWS) as News
            (shuffle as ShuffleAdapter).clearAndAddNews(apiNews!!.news)
            //(news_list as NewsAdapter).clearAndAddNews(apiNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //val news = (news_list.adapter as NewsAdapter).getNews()
        val news = (shuffle.shuffleAdapter as ShuffleAdapter).news
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
                    //(news_list.adapter as NewsAdapter).addNews(retrievedNews.news)},
                    (shuffle as ShuffleAdapter).addNews(retrievedNews.news) },
                {e -> Snackbar.make(shuffle, e.message ?: "", Snackbar.LENGTH_LONG).show()
                Log.e("CONNECTION", e.message ?: "")}
            )

        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if(shuffle.shuffleAdapter == null) {
            shuffle.shuffleAdapter = ShuffleAdapter()
        }

//        if (news_list.adapter == null) {
//            news_list.adapter = NewsAdapter()
//        }
    }
}