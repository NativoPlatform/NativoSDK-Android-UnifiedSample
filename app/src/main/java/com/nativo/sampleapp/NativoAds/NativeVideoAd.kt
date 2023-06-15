package com.nativo.sampleapp.NativoAds

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.TextureView
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

    // Nativo Injectable properties
    override lateinit var view: View
    override lateinit var videoContainer : FrameLayout
    override lateinit var titleLabel: TextView
    override lateinit var authorLabel: TextView
    override lateinit var adChoicesImageView: ImageView
    override lateinit var authorImageView: ImageView
    override var dateLabel: TextView? = null
    override var previewTextLabel: TextView? = null
    override val shouldPrependAuthorByline: Boolean = true

    // Nativo Video Injectable properties
    override lateinit var playButton: ImageView
    override lateinit var restartButton: ImageView
    override lateinit var progressBar: ProgressBar

    override fun getLayout(context: Context): Int {
        return R.layout.video_layout_2
    }

    override fun bindViews(v: View) {
        view = v
        titleLabel = v.findViewById(R.id.article_title)
        authorLabel = v.findViewById(R.id.article_author)
        authorImageView = v.findViewById(R.id.article_author_image)
        adChoicesImageView = v.findViewById(R.id.adchoices_indicator)
        playButton = v.findViewById(R.id.play)
        restartButton = v.findViewById(R.id.restart)
        progressBar = v.findViewById(R.id.video_progress_bar)
        videoContainer = v.findViewById(R.id.video_container)
    }

    override val videoEventListener: VideoEventListener = object : VideoEventListener {
        override fun onVideoStateChange(state: VideoState, player: VideoPlayer) {
            if (state == VideoState.Init) {
                player.setResizeMode(VideoResizeMode.Fit)
            }
        }

        override fun onVideoProgress(progress: Long, player: VideoPlayer) {
        }

        override fun onVideoError(error: VideoPlaybackError, player: VideoPlayer) {
        }

        override fun onVideoFullscreen(player: VideoPlayer) {
        }

        override fun onVideoExitFullscreen(player: VideoPlayer) {
        }

        override fun onVideoLearnMore(player: VideoPlayer) {
        }
    }

    // Only use this if you need to switch between regular article and sponsored nativo ads
    override fun displaySponsoredIndicators(isSponsored: Boolean) {
    }

    override fun formatDate(date: Date?): String? {
        return null
    }
}