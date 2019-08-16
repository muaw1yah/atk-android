package com.atakaice.features.news.adapter

import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.NewsItem
import com.atakaice.commons.adapter.ViewType
import com.atakaice.commons.adapter.ViewTypeDelegateAdapter
import com.atakaice.commons.extensions.getFriendlyTime
import com.atakaice.commons.extensions.inflate
import com.atakaice.commons.extensions.loading
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

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
            try {
                img_thumbnail.loading(item.files.get(0))
                var title = if (item.title.length > 100) item.title.substring(100)+"..." else item.title
                description.text = title + " - " + item.author

                var d = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(item.pubDate)
                time.text = (d.time / 1000).getFriendlyTime()
            } catch (e: Exception) {
                Log.e("BIND", e.message)
            }
        }

    }
}