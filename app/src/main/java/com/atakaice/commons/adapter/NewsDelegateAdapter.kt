package com.atakaice.commons.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.extensions.getFriendlyTime
import com.atakaice.commons.extensions.inflate
import com.atakaice.commons.extensions.loading
import com.atakaice.commons.models.NewsItem
import kotlinx.android.synthetic.main.news_item.view.*

class NewsDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as NewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item)
    ) {

        fun bind(item: NewsItem) = with(itemView) {
            img_thumbnail.loading(item.thumbnail)
            description.text = item.title
            author.text = item.author
            time.text = item.created.getFriendlyTime()
        }

    }
}