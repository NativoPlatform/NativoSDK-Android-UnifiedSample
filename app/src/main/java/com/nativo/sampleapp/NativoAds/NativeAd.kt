package com.nativo.sampleapp.NativoAds

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvNativeAdInjectable
import java.text.SimpleDateFormat
import java.util.*

class NativeAd : NtvNativeAdInjectable {

    // Nativo Injectable properties
    override lateinit var view: View
    override lateinit var titleLabel: TextView
    override lateinit var authorLabel: TextView
    override lateinit var adChoicesImageView: ImageView
    override lateinit var authorImageView: ImageView
    override var dateLabel: TextView? = null
    override lateinit var previewImageView: ImageView
    override lateinit var previewTextLabel: TextView
    override val shouldPrependAuthorByline: Boolean = true

    // Misc
    private var sponsoredTag: TextView? = null

    override fun getLayout(context: Context): Int {
        return R.layout.native_article_2
    }

    override fun bindViews(v: View) {
        view = v
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        previewImageView = v.findViewById(R.id.article_image)
        previewTextLabel = v.findViewById(R.id.article_description)
        authorImageView = v.findViewById(R.id.article_author_image)
        sponsoredTag = v.findViewById(R.id.sponsored_tag)
        adChoicesImageView = v.findViewById(R.id.adchoices_indicator)
    }

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
    }

    override fun formatDate(date: Date?): String? {
        if (date == null) return null
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }
}