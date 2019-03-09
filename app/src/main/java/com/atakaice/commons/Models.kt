package com.atakaice.commons

import android.os.Parcel
import android.os.Parcelable
import com.atakaice.commons.adapter.AdapterConstants
import com.atakaice.commons.adapter.ViewType
import com.atakaice.commons.extensions.createParcel

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class News(
    val after: String,
    val before: String,
    val news: List<NewsItem>
) : Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { News(it) }
    }

    constructor(parcelIn: Parcel) : this(
        parcelIn.readString(),
        parcelIn.readString(),
        mutableListOf<NewsItem>().apply {
            parcelIn.readTypedList(this, NewsItem.CREATOR)
        }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(news)
    }

    override fun describeContents() = 0
}


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class NewsItem(
    val author: String,
    val title: String,
//    val pubDate: Date,
    val link: String,
    val description: String,
    val category: String,
    val files: List<String>
) : ViewType, Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { NewsItem(it) }
    }

    constructor(parcelIn: Parcel) : this(
        parcelIn.readString(),
        parcelIn.readString(),
        // parcelIn.readString(),
        parcelIn.readString(),
        parcelIn.readString(),
        parcelIn.readString(),
        mutableListOf<String>()
    )

    override fun writeToParcel(dest: Parcel, p1: Int) {
        dest.writeString(author)
        dest.writeString(title)
        dest.writeString(link)
        dest.writeString(description)
        dest.writeString(category)
        dest.writeArray(arrayOf(files))
    }

    override fun describeContents() = 0

    override fun getViewType() = AdapterConstants.NEWS
}