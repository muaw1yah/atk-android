package com.atakaice.features.news.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.NewsItem
import com.atakaice.commons.adapter.ViewType
import com.atakaice.commons.adapter.ViewTypeCardsDelegateAdapter
import com.atakaice.commons.extensions.inflate
import com.atakaice.commons.extensions.loading
import com.meetic.shuffle.Shuffle
import kotlinx.android.synthetic.main.news_item.view.*

class NewsCardDelegateAdapter: ViewTypeCardsDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): Shuffle.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: Shuffle.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as NewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup): Shuffle.ViewHolder(
        parent.inflate(R.layout.news_card)
    ) {

        fun bind(item: NewsItem) = with(itemView) {
            img_thumbnail.loading(item.files.get(0))
            description.text = item.title
            //author.text = item.author
            // time.text = item.pubDate.toLong().getFriendlyTime()
        }

    }
}