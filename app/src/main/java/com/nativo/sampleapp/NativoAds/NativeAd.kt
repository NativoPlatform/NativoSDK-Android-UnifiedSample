package com.nativo.sampleapp.NativoAds

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
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
    override lateinit var dateLabel: TextView
    override lateinit var previewImageView: ImageView
    override lateinit var previewTextLabel: TextView
    override val shouldPrependAuthorByline: Boolean = true

    // Misc
    private var sponsoredTag: TextView? = null
    private var cardView: CardView? = null

    override fun getLayout(context: Context): Int {
        return R.layout.native_article
    }

    override fun bindViews(v: View) {
        view = v
        cardView = v.findViewById(R.id.article_constraint_layout)
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        previewImageView = v.findViewById(R.id.article_image)
        dateLabel = v.findViewById(R.id.article_date)
        previewTextLabel = v.findViewById(R.id.article_description)
        authorImageView = v.findViewById(R.id.article_author_image)
        sponsoredTag = v.findViewById(R.id.sponsored_tag)
        adChoicesImageView = v.findViewById(R.id.adchoices_indicator)
    }

    // Use this to toggle between regular article and nativo sponsored ad
    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        if (isSponsored) {
            cardView?.setBackgroundColor(Color.LTGRAY)
            sponsoredTag?.visibility = View.VISIBLE
        } else {
            cardView?.setBackgroundColor(Color.WHITE)
            sponsoredTag?.visibility = View.INVISIBLE
        }
    }

    override fun formatDate(date: Date?): String? {
        if (date == null) return null
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }
}