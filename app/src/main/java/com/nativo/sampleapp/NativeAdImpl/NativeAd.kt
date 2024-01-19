package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvNativeAdInjectable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NativeAd : NtvNativeAdInjectable {

    private val cardView: CardView? = null
    override lateinit var titleLabel: TextView
    override lateinit var authorLabel: TextView
    override lateinit var authorImageView: ImageView
    override lateinit var previewImageView: ImageView
    override var isiContentView: FrameLayout? = null
    override val previewTextLabel: TextView? = null
    override val dateLabel: TextView? = null
    override val adChoicesImageView: ImageView? = null
    private val sponsoredTag: TextView? = null
    override lateinit var view: View

    override fun displaySponsoredIndicators(b: Boolean) {
        cardView?.setBackgroundColor(Color.LTGRAY)
        if (sponsoredTag != null) {
            sponsoredTag.visibility = View.VISIBLE
        }
    }

    override fun formatDate(date: Date?): String? {
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date)
    }

    override fun getLayout(context: Context): Int {
        return R.layout.native_article_2
    }

    override fun bindViews(v: View) {
        view = v
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        previewImageView = v.findViewById(R.id.article_image)
        authorImageView = v.findViewById(R.id.article_author_image)
//        isiContentView = v.findViewById(R.id.isi_content)

//        layout = v.findViewById(R.id.article_layout);
//        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator);
//        sponsoredTag = v.findViewById(R.id.sponsored_tag);
//        articleDateLabel = v.findViewById(R.id.article_date);
//        articlePreviewLabel = v.findViewById(R.id.article_description);
    }

    override val shouldPrependAuthorByline: Boolean
        get() = true
}