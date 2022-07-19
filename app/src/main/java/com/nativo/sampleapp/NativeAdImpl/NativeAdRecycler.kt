package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import android.widget.TextView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder
import net.nativo.sdk.injectable.NtvNativeAdInjectable
import java.util.*

class NativeAdRecycler(
    private val adContainerView: View, viewGroup: ViewGroup
) : ArticleViewHolder(
    adContainerView, viewGroup
), NtvNativeAdInjectable {

    private lateinit var _view: View
    private var layout: LinearLayout? = null
    private var cardView: CardView? = null
    private lateinit var _titleLabel: TextView
    private lateinit var _authorLabel: TextView
    private var _articlePreviewLabel: TextView? = null
    private var _articleDateLabel: TextView? = null
    private var _articleAuthorImage: ImageView? = null
    private var _image: ImageView? = null
    private lateinit var _adChoicesIndicator: ImageView
    private var sponsoredTag: TextView? = null

    override val adChoicesImageView: ImageView
        get() = _adChoicesIndicator
    override val authorImageView: ImageView?
        get() = _articleAuthorImage
    override val authorLabel: TextView
        get() = _authorLabel
    override val dateLabel: TextView?
        get() = _articleDateLabel
    override val previewImageView: ImageView?
        get() = _image
    override val previewTextLabel: TextView?
        get() = _articlePreviewLabel
    override val titleLabel: TextView
        get() = _titleLabel
    override val view: View
        get() = _view

    init {
        bindViews(itemView)
    }

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        if (cardView != null) {
            cardView!!.setBackgroundColor(Color.LTGRAY)
        } else view.setBackgroundColor(Color.LTGRAY)

        sponsoredTag?.visibility = View.VISIBLE
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

    override fun getLayout(context: Context): Int {
        return R.layout.native_article
    }

    override fun bindViews(v: View) {
        _view = v
        layout = v.findViewById(R.id.article_layout)
        cardView = v.findViewById(R.id.article_constraint_layout);
        _titleLabel = v.findViewById(R.id.article_title)
        _authorLabel = v.findViewById(R.id.article_author)
        _image = v.findViewById(R.id.article_image)
        _articleDateLabel = v.findViewById(R.id.article_date)
        _articlePreviewLabel = v.findViewById(R.id.article_description)
        _articleAuthorImage = v.findViewById(R.id.article_author_image)
        sponsoredTag = v.findViewById(R.id.sponsored_tag)
        _adChoicesIndicator = v.findViewById(R.id.adchoices_indicator)
    }

}