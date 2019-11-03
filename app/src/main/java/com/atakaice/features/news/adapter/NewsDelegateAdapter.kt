package com.atakaice.features.news.adapter

import android.annotation.SuppressLint
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

        @SuppressLint("SimpleDateFormat")
        fun bind(item: NewsItem) = with(itemView) {
            try {
                img_thumbnail.loading(item.files)
                description.text = item.title

                val d = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(item.pubDate)
                time.text = (d.time / 1000).getFriendlyTime()

                when (item.author.trim().toLowerCase()) {
                    "bbc" -> sourceImage.setImageResource(R.drawable.bbc)
                    "rfi" -> sourceImage.setImageResource(R.drawable.rfi)
                    "voa" -> sourceImage.setImageResource(R.drawable.voa)
                    "dw" -> sourceImage.setImageResource(R.drawable.dw)
                    else -> Log.i("SOURCE", item.author)
                }
            } catch (e: Exception) {
                Log.e("BIND", e.message)
            }
        }

    }
}