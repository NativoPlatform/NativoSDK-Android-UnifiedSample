package com.nativo.sampleapp.NativoAds

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvVideoAdInjectable
import net.nativo.sdk.video.VideoPlaybackError
import java.util.*

class NativeVideoAd : NtvVideoAdInjectable {

    companion object {
        private val TAG = NativeVideoAd::class.java.name
    }

    private var layout: RelativeLayout? = null
    private lateinit var _textureView: TextureView
    private lateinit var _previewImage: ImageView
    private lateinit var _playButton: ImageView
    private lateinit var _restartButton: ImageView
    private var _muteIndicator: ImageView? = null
    private lateinit var _titleLabel: TextView
    private lateinit var _authorLabel: TextView
    private val sponsoredIndicator: ImageView? = null
    private var _adChoicesIndicator: ImageView? = null
    private var _progressBar: ProgressBar? = null
    private var _view: View? = null
    private var _articlePreviewLabel: TextView? = null
    private var _articleAuthorImage: ImageView? = null
    private var _articleDateLabel: TextView? = null
    private var cardView: CardView? = null

    override val textureView: TextureView
        get() = _textureView
    override val previewImage: ImageView
        get() = _previewImage
    override val previewTextLabel: TextView?
        get() = _articlePreviewLabel
    override val playButton: ImageView
        get() = _playButton
    override val restartButton: ImageView
        get() = _restartButton
    override val muteIndicator: ImageView?
        get() = _muteIndicator
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
    override val progressBar: ProgressBar?
        get() = _progressBar
    override val view: View?
        get() = _view
    override val shouldPrependAuthorByline: Boolean = true

    override fun getLayout(context: Context): Int {
        return R.layout.video_layout
    }

    override fun bindViews(v: View) {
        _view = v
        layout = v.findViewById(R.id.video_container)
        _textureView = v.findViewById(R.id.video)
        _previewImage = v.findViewById(R.id.preview_image)
        _playButton = v.findViewById(R.id.play)
        _restartButton = v.findViewById(R.id.restart)
        _titleLabel = v.findViewById(R.id.article_title)
        _authorLabel = v.findViewById(R.id.article_author)
        _progressBar = v.findViewById(R.id.video_progress_bar)
        _muteIndicator = v.findViewById(R.id.mute_indicator)
        _adChoicesIndicator = v.findViewById(R.id.adchoices_indicator)
        _articlePreviewLabel = v.findViewById(R.id.article_description)
        _articleAuthorImage = v.findViewById(R.id.article_author_image)
        _articleDateLabel = v.findViewById(R.id.article_date)
        cardView = v.findViewById(R.id.video_constraint_layout)
    }

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        cardView?.setBackgroundColor(Color.LTGRAY)
    }

    override fun formatDate(date: Date?): String? {
        return null
    }

    override fun onVideoEnteredFullscreen() {
        Log.d(TAG, "onVideoEnteredFullscreen: ")
    }

    override fun onVideoExitedFullscreen() {
        Log.d(TAG, "onVideoExitedFullscreen: ")
    }

    override fun onVideoPlay() {
        Log.d(TAG, "onVideoPlay: ")
    }

    override fun onVideoPause() {
        Log.d(TAG, "onVideoPause: ")
    }

    override fun onVideoPlaybackCompleted() {
        Log.d(TAG, "onVideoPlaybackCompleted: ")
    }

    override fun onVideoPlaybackError(error: VideoPlaybackError?) {
        Log.e("NativoSDK", "onVideoPlaybackError: $error")
    }

}