package com.atakaice.commons.adapter

import android.view.ViewGroup
import com.meetic.shuffle.Shuffle

interface ViewTypeCardsDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): Shuffle.ViewHolder

    fun onBindViewHolder(holder: Shuffle.ViewHolder, item: ViewType)
}