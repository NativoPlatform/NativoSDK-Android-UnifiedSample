package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder
import net.nativo.sdk.ntvadtype.nativead.NtvNativeAdInterface
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import android.widget.TextView
import com.nativo.sampleapp.R
import java.text.SimpleDateFormat
import java.util.*

class NativeAdRecycler(
    private val adContainerView: View, viewGroup: ViewGroup
) : RecyclerListViewHolder(
    adContainerView, viewGroup
), NtvNativeAdInterface {

    private var layout: LinearLayout? = null
    private var cardView: CardView? = null
    private var titleLabel: TextView? = null
    private var authorLabel: TextView? = null
    private var articlePreviewLabel: TextView? = null
    private var articleDateLabel: TextView? = null
    private var articleAuthorImage: ImageView? = null
    private var image: ImageView? = null
    private var adChoicesIndicator: ImageView? = null
    private var sponsoredTag: TextView? = null
    var view: View? = null
        private set

    init {
        bindViews(itemView)
    }

    override fun getTitleLabel(): TextView? {
        if (titleLabel == null) {
            titleLabel = view?.findViewById<View>(R.id.article_title) as TextView
        }

        return titleLabel
    }

    override fun getAdContainerView(): View {
        return adContainerView
    }

    override fun getAuthorLabel(): TextView? {
        return authorLabel
    }

    override fun getPreviewTextLabel(): TextView? {
        return articlePreviewLabel
    }

    override fun getPreviewImageView(): ImageView? {
        return image
    }

    override fun getAuthorImageView(): ImageView? {
        return articleAuthorImage
    }

    override fun getDateLabel(): TextView? {
        return articleDateLabel
    }

    override fun displaySponsoredIndicators(b: Boolean) {
        if (cardView != null) {
            cardView!!.setBackgroundColor(Color.LTGRAY)
        } else if (view != null) {
            view!!.setBackgroundColor(Color.LTGRAY)
        }

        sponsoredTag?.visibility = View.VISIBLE
    }

    override fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }

    override fun getLayout(context: Context): Int {
        return R.layout.native_article
    }

    override fun getAdChoicesImageView(): ImageView? {
        return adChoicesIndicator
    }

    override fun bindViews(v: View) {
        view = v
        layout = v.findViewById(R.id.article_layout)
        cardView = v.findViewById(R.id.article_constraint_layout)
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        image = v.findViewById(R.id.article_image)
        articleDateLabel = v.findViewById(R.id.article_date)
        articlePreviewLabel = v.findViewById(R.id.article_description)
        articleAuthorImage = v.findViewById(R.id.article_author_image)
        sponsoredTag = v.findViewById(R.id.sponsored_tag)
        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator)
    }

}