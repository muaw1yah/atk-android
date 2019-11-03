package com.atakaice.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.atakaice.R
import com.atakaice.commons.NewsItem
import com.atakaice.commons.extensions.loading
import kotlinx.android.synthetic.main.activity_news.*
import java.text.SimpleDateFormat
import java.util.*
import android.webkit.WebView
import android.webkit.WebViewClient


class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        var newsItem: NewsItem = intent.getParcelableExtra("CLICKED_NEWS")

        if(newsItem.files.isNotEmpty()) {
            try {
                titleText.text = newsItem.title
                descriptionText.text = newsItem.description
                img_thumbnail.loading(newsItem.files)

                val d = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(newsItem.pubDate)
                val f = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.getDefault())

                //timeText.text = (d.time / 1000).getFriendlyTime()
                timeText.text = f.format(d.time).toString()
//                readNews.setBackgroundResource(R.drawable.coffee_bg)
                initViews(newsItem)
            } catch (e: Exception) {
                Log.e("NEWS", e.message)
                finish()
            }
        }
    }

    private fun initViews(newsItem: NewsItem) {

        webView.apply {
            settings.apply {
                useWideViewPort
                loadWithOverviewMode
                supportZoom()
                builtInZoomControls
                displayZoomControls
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                }
            }
        }

        readNews.setOnClickListener {
            webViewBox.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            webView.loadUrl(newsItem.link.trim())
        }

        closeWebView.setOnClickListener {
            webViewBox.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if(webView.visibility == View.VISIBLE) {
            webView.visibility = View.GONE
            return
        }

        super.onBackPressed()
    }
}
