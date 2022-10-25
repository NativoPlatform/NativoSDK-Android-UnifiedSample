package com.nativo.sampleapp.NativoAds

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvVideoAdInjectable
import net.nativo.sdk.video.VideoPlaybackError
import java.util.*

class NativeVideoAd : NtvVideoAdInjectable {

    // Nativo Injectable properties
    override lateinit var view: View
    override lateinit var titleLabel: TextView
    override lateinit var authorLabel: TextView
    override lateinit var adChoicesImageView: ImageView
    override lateinit var authorImageView: ImageView
    override lateinit var dateLabel: TextView
    override lateinit var previewTextLabel: TextView
    override lateinit var previewImage: ImageView
    override val shouldPrependAuthorByline: Boolean = true

    // Nativo Video Injectable properties
    override lateinit var textureView: TextureView
    override lateinit var playButton: ImageView
    override lateinit var restartButton: ImageView
    override lateinit var muteIndicator: ImageView
    override lateinit var progressBar: ProgressBar

    // Misc
    private var cardView: CardView? = null

    override fun getLayout(context: Context): Int {
        return R.layout.video_layout
    }

    override fun bindViews(v: View) {
        view = v
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        authorImageView = v.findViewById(R.id.article_author_image)
        previewTextLabel = v.findViewById(R.id.article_description)
        previewImage = v.findViewById(R.id.preview_image)
        adChoicesImageView = v.findViewById(R.id.adchoices_indicator)
        dateLabel = v.findViewById(R.id.article_date)
        textureView = v.findViewById(R.id.video)
        playButton = v.findViewById(R.id.play)
        restartButton = v.findViewById(R.id.restart)
        muteIndicator = v.findViewById(R.id.mute_indicator)
        progressBar = v.findViewById(R.id.video_progress_bar)

        cardView = v.findViewById(R.id.video_constraint_layout)
        cardView?.setBackgroundColor(Color.LTGRAY)
    }

    // Only use this if you need to switch between regular article and sponsored nativo ads
    override fun displaySponsoredIndicators(isSponsored: Boolean) {
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

    private val tag = "NativeVideoAd"
    override fun onVideoEnteredFullscreen() {
        Log.d(tag, "onVideoEnteredFullscreen: ")
    }

    override fun onVideoExitedFullscreen() {
        Log.d(tag, "onVideoExitedFullscreen: ")
    }

    override fun onVideoPlay() {
        Log.d(tag, "onVideoPlay: ")
    }

    override fun onVideoPause() {
        Log.d(tag, "onVideoPause: ")
    }

    override fun onVideoPlaybackCompleted() {
        Log.d(tag, "onVideoPlaybackCompleted: ")
    }

    override fun onVideoPlaybackError(error: VideoPlaybackError?) {
        Log.e(tag, "onVideoPlaybackError: $error")
    }

}