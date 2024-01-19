package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder
import net.nativo.sdk.injectable.NtvNativeAdInjectable
import java.util.Date

class NativeAdRecycler(private val adContainerView: View, viewGroup: ViewGroup?) :
    ArticleViewHolder(
        adContainerView, viewGroup
    ), NtvNativeAdInjectable {

    override lateinit var view: View
    override lateinit var titleLabel: TextView
    override lateinit var authorLabel: TextView
    override lateinit var authorImageView: ImageView
    override lateinit var previewImageView: ImageView
    private var cardView: CardView? = null

    override var previewTextLabel: TextView? = null
        private set
    override var dateLabel: TextView? = null
        private set
    override var adChoicesImageView: ImageView? = null
        private set
    override val isiContentView: FrameLayout? = null
    private var sponsoredTag: TextView? = null


    init {
        bindViews(itemView)
    }


    override fun displaySponsoredIndicators(b: Boolean) {
        if (cardView != null) {
            cardView!!.setBackgroundColor(Color.LTGRAY)
        } else if (view != null) {
            view!!.setBackgroundColor(Color.LTGRAY)
        }
        if (sponsoredTag != null) {
            sponsoredTag!!.visibility = View.VISIBLE
        }
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

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

    override val shouldPrependAuthorByline: Boolean
        get() = false
}