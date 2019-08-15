package com.atakaice.features.news;


import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.atakaice.R;
import com.atakaice.commons.News;
import com.atakaice.commons.NewsItem;
import com.atakaice.commons.RxBaseFragment;
import com.atakaice.features.news.adapter.ShuffleAdapter;
import com.meetic.shuffle.Shuffle;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsCardsFragment extends RxBaseFragment {

    private final String KEY_NEWS = "news";
    private News apiNews;
    private NewsManager newsManager;
    private Shuffle shuffle;

    public NewsCardsFragment() {
        // Required empty public constructor
        apiNews = null;
        newsManager = new NewsManager();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_cards, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //setup adapter
        shuffle = getView().findViewById(R.id.shuffle);
        shuffle.setShuffleAdapter(new ShuffleAdapter());

        initAdapter();

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY_NEWS)) {
            apiNews = (News)savedInstanceState.get(KEY_NEWS);
            ((ShuffleAdapter)shuffle.getShuffleAdapter()).clearAndAddNews(apiNews.getNews());
        } else {
            requestNews();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        List<NewsItem> newsItems = ((ShuffleAdapter)shuffle.getShuffleAdapter()).getNews();
        if(apiNews != null && !newsItems.isEmpty()) {
            outState.putParcelable(KEY_NEWS, (Parcelable) apiNews);
        }

    }

    public void requestNews() {
        final String after = apiNews == null ? "" : apiNews.getAfter();
        Subscription subscription = newsManager.getNews(after, "10")
               .subscribeOn(Schedulers.io())
               .subscribe(
                       new Action1<News>() {
                           @Override
                           public void call(News retrivedNews) {
                               apiNews = retrivedNews;
                               ((ShuffleAdapter)shuffle.getShuffleAdapter()).addNews(retrivedNews.getNews());
                           }
                       });

        this.getSubscriptions().add(subscription);
    }

    private void initAdapter() {
        if(shuffle.getShuffleAdapter() == null) {
            shuffle.setShuffleAdapter(new ShuffleAdapter());
        }
    }
}
