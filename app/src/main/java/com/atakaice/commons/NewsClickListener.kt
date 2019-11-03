package com.atakaice.commons

import android.content.Intent
import android.view.View
import com.atakaice.actvities.NewsActivity

class NewsClickListener(var newsItem: NewsItem) : View.OnClickListener  {
    override fun onClick(view: View?) {
        if(this.newsItem.files.isNotEmpty()) {
            if (view != null) {
                val intent = Intent(view.context, NewsActivity::class.java)
                intent.putExtra("CLICKED_NEWS", newsItem)
                view.context.startActivity(intent)
            }
        }
    }

}