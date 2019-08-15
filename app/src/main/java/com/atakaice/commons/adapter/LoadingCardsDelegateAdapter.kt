package com.atakaice.commons.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.atakaice.R
import com.atakaice.commons.extensions.inflate
import com.meetic.shuffle.Shuffle

class LoadingCardsDelegateAdapter: ViewTypeCardsDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = TurnsViewHolder(parent)

    override fun onBindViewHolder(holder: Shuffle.ViewHolder, item: ViewType) {
    }

    class TurnsViewHolder(parent: ViewGroup) : Shuffle.ViewHolder(
        parent.inflate(R.layout.news_item_loading)){
    }

}