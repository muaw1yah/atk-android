package com.atakaice.features.news_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.atakaice.R;
import com.atakaice.commons.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class SwipeStackAdapter extends BaseAdapter {

    private List<NewsItem> mData;
    private Context context;

    public SwipeStackAdapter(Context context, List<NewsItem> data) {
        this.mData = data == null ? new ArrayList<NewsItem>() : data;
        this.context = context;
    }

    public SwipeStackAdapter(Context context) {
        this.mData = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public List<NewsItem> getData() {
        return mData;
    }

    @Override
    public NewsItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.news_card, parent, false);
        }

        NewsItem currentItem = (NewsItem) getItem(position);

        TextView description = convertView.findViewById(R.id.description);
        TextView author = convertView.findViewById(R.id.author);
        TextView time = convertView.findViewById(R.id.time);

        description.setText(currentItem.getDescription());
        author.setText(currentItem.getAuthor());

        return convertView;
    }
}