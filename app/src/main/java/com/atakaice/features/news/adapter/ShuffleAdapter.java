package com.atakaice.features.news.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.SparseArrayCompat;
import com.atakaice.R;
import com.atakaice.commons.NewsItem;
import com.atakaice.commons.adapter.*;
import com.meetic.shuffle.Shuffle;
import com.atakaice.features.news.adapter.NewsCardDelegateAdapter.TurnsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ShuffleAdapter extends Shuffle.Adapter<TurnsViewHolder> {

    private List<ViewType> items = new ArrayList<>();
    private SparseArrayCompat<ViewTypeCardsDelegateAdapter> delegateAdapters = new SparseArrayCompat<>();
    private ViewType loadingItem = new ViewType() {
        @Override
        public int getViewType() {
            return 2;
        }
    };

    public ShuffleAdapter() {
        delegateAdapters.put(2, new LoadingCardsDelegateAdapter());
        delegateAdapters.put(1, new NewsCardDelegateAdapter());

        items = new ArrayList<>();
        items.add(loadingItem);
    }

    @Override
    public TurnsViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card, viewGroup, false);
        return new TurnsViewHolder((ViewGroup) view);
    }

    @Override
    public void onBindViewHolder(TurnsViewHolder viewHolder, int position) {
//        if(getItemViewType(position) == 1) {
//            NewsItem item = this.items.get(position);
//
//        } else {
//
//        }
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(viewHolder, this.items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addNews(final List<NewsItem> news) {
        // first remove loading and notify
        int initPosition = items.size() - 1;
        items.remove(initPosition);
        // insert news and the loading at the end of the list

        items.addAll(news);
        items.add(loadingItem);
        //notifyDataSetChanged();
    }

    public void clearAndAddNews(final List<NewsItem> news) {
        items.clear();
        notifyDataSetChanged();

        items.addAll(news);
        items.add(loadingItem);
        notifyDataSetChanged();
    }

    public List<NewsItem> getNews() {
        List<NewsItem> filteredNes = new ArrayList<>();
        for(ViewType ni: items) {
            if(ni.getViewType() == 2) {
                filteredNes.add((NewsItem) ni);
            }
        }

        return filteredNes;
    }
}
