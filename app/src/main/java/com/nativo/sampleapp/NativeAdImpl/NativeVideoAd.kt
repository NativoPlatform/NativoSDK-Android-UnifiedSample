package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvVideoAdInjectable
import net.nativo.sdk.video.*
import java.util.*

class NativeVideoAd : NtvVideoAdInjectable {
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

    private var cardView: CardView? = null

    override fun getLayout(context: Context): Int {
        return R.layout.video_layout
    }

    override fun bindViews(v: View) {
        view = v
        videoContainer = v.findViewById(R.id.video_container)
        playButton = v.findViewById(R.id.play)
        restartButton = v.findViewById(R.id.restart)
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        progressBar = v.findViewById(R.id.video_progress_bar)
        adChoicesImageView = v.findViewById(R.id.adchoices_indicator)
        previewTextLabel = v.findViewById(R.id.article_description)
        authorImageView = v.findViewById(R.id.article_author_image)
        dateLabel = v.findViewById(R.id.article_date)
        cardView = v.findViewById(R.id.video_constraint_layout)
    }

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        if (cardView != null) {
            cardView!!.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

    override val shouldPrependAuthorByline: Boolean
        get() = true

    //Log.d(TAG, "onVideoProgress: " + progress);
    override val videoEventListener: VideoEventListener = object : VideoEventListener {

        override fun onVideoLoaded(player: VideoPlayer) {
            player.setResizeMode(VideoResizeMode.Fit)
            Log.d(TAG, "onVideoLoaded: ")
        }

        override fun onVideoStateChange(state: VideoState, player: VideoPlayer) {}

        override fun onVideoProgress(progress: Long, player: VideoPlayer) {}

        override fun onVideoError(error: VideoPlaybackError, player: VideoPlayer) {
            Log.d(TAG, "onVideoError: $error")
        }

        override fun onVideoFullscreen(player: VideoPlayer) {
            Log.d(TAG, "onVideoFullscreen: ")
        }

        override fun onVideoExitFullscreen(player: VideoPlayer) {
            Log.d(TAG, "onVideoExitFullscreen: ")
        }

        override fun onVideoLearnMore(player: VideoPlayer) {
            Log.d(TAG, "onVideoLearnMore: ")
        }
    }

    companion object {
        private val TAG = NativeVideoAd::class.java.name
    }
}