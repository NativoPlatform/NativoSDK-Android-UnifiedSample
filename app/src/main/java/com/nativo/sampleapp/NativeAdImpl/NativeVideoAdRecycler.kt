package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder
import net.nativo.sdk.injectable.NtvVideoAdInjectable
import net.nativo.sdk.video.VideoEventListener
import java.util.Date

class NativeVideoAdRecycler(container: View, viewGroup: ViewGroup?) :
    ArticleViewHolder(container, viewGroup), NtvVideoAdInjectable {

    override lateinit var videoContainer: FrameLayout
    override lateinit var playButton: ImageView
    override lateinit var restartButton: ImageView
    override lateinit var titleLabel: TextView
    override lateinit var authorLabel: TextView
    override lateinit var adChoicesImageView: ImageView
    override lateinit var progressBar: ProgressBar
    override lateinit var view: View
    override lateinit var previewTextLabel: TextView
    override lateinit var authorImageView: ImageView
    override lateinit var dateLabel: TextView
    override val isiContentView: FrameLayout? = null
    override fun getLayout(context: Context): Int {
        return 0
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun bindViews(v: View) {
        view = v
        playButton = v.findViewById<View>(R.id.play) as ImageView
        restartButton = v.findViewById<View>(R.id.restart) as ImageView
        titleLabel = v.findViewById<View>(R.id.article_title) as TextView
        authorLabel = v.findViewById<View>(R.id.article_author) as TextView
        progressBar = v.findViewById(R.id.video_progress_bar)
        adChoicesImageView = v.findViewById(R.id.adchoices_indicator)
        previewTextLabel = v.findViewById(R.id.article_description)
        authorImageView = v.findViewById(R.id.article_author_image)
        dateLabel = v.findViewById(R.id.article_date)
    }

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        view.setBackgroundColor(Color.LTGRAY)
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

    override val shouldPrependAuthorByline: Boolean
        get() = false
    override val videoEventListener: VideoEventListener?
        get() = null

    companion object {
        private val TAG = NativeVideoAdRecycler::class.java.name
    }
}