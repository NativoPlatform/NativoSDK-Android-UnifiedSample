package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import android.widget.TextView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvNativeAdInjectable
import java.text.SimpleDateFormat
import java.util.*

class NativeAd : NtvNativeAdInjectable {
    private var _view: View? = null
    private lateinit var _titleLabel: TextView
    private lateinit var _authorLabel: TextView
    private var _adChoicesIndicator: ImageView? = null
    private var _articleDateLabel: TextView? = null
    private var _articlePreviewLabel: TextView? = null
    private var _articleAuthorImage: ImageView? = null
    private var _articleImage: ImageView? = null

    private var layout: LinearLayout? = null
    private var cardView: CardView? = null
    override val titleLabel: TextView
        get() = _titleLabel
    override val adChoicesImageView: ImageView?
        get() = _adChoicesIndicator
    override val authorImageView: ImageView?
        get() = _articleAuthorImage
    override val authorLabel: TextView
        get() = _authorLabel
    override val dateLabel: TextView?
        get() = _articleDateLabel
    override val previewImageView: ImageView?
        get() = _articleImage
    override val previewTextLabel: TextView?
        get() = _articlePreviewLabel

    private var adChoicesIndicator: ImageView? = null
    private var sponsoredTag: TextView? = null
    override var view: View? = _view

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        cardView?.setBackgroundColor(Color.LTGRAY)
        sponsoredTag?.visibility = View.VISIBLE
    }

    override fun formatDate(date: Date?): String? {
        if (date == null) return null
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }

    override fun getLayout(context: Context): Int {
        return R.layout.native_article
    }

    override fun bindViews(v: View) {
        _view = v
        layout = v.findViewById(R.id.article_layout)
        cardView = v.findViewById(R.id.article_constraint_layout)
        _titleLabel = v.findViewById(R.id.article_title)
        _authorLabel = v.findViewById(R.id.article_author)
        _articleImage = v.findViewById(R.id.article_image)
        _articleDateLabel = v.findViewById(R.id.article_date)
        _articlePreviewLabel = v.findViewById(R.id.article_description)
        _articleAuthorImage = v.findViewById(R.id.article_author_image)
        sponsoredTag = v.findViewById(R.id.sponsored_tag)
        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator)
    }
}