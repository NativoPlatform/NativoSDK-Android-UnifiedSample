package com.nativo.sampleapp.NativeAdImpl

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder
import net.nativo.sdk.ntvadtype.video.NtvVideoAdInterface
import net.nativo.sdk.ntvadtype.video.VideoPlaybackError
import java.util.*

class NativeVideoAdRecycler(private val view: View, viewGroup: ViewGroup) : RecyclerListViewHolder(
    view, viewGroup
), NtvVideoAdInterface {

    companion object {
        private val TAG = NativeVideoAdRecycler::class.java.name
    }

    private var layout: RelativeLayout? = null
    private var textureView: TextureView? = null
    private var previewImage: ImageView? = null
    private var playButton: ImageView? = null
    private var restartButton: ImageView? = null
    private var muteIndicator: ImageView? = null
    private var titleLabel: TextView? = null
    private var authorLabel: TextView? = null
    private val sponsoredIndicator: ImageView? = null
    private var adChoicesIndicator: ImageView? = null
    private var articlePreviewLabel: TextView? = null
    private var articleAuthorImage: ImageView? = null
    private var articleDateLabel: TextView? = null
    private var progressBar: ProgressBar? = null
    private var cardView: CardView? = null

    override fun getLayout(context: Context): Int {
        return 0
    }

    override fun getAdContainerView(): View {
        return view
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun bindViews(v: View) {
        layout = v.findViewById<View>(R.id.video_container) as RelativeLayout
        textureView = v.findViewById<View>(R.id.video) as TextureView
        previewImage = v.findViewById<View>(R.id.preview_image) as ImageView
        playButton = v.findViewById<View>(R.id.play) as ImageView
        restartButton = v.findViewById<View>(R.id.restart) as ImageView
        titleLabel = v.findViewById<View>(R.id.article_title) as TextView
        authorLabel = v.findViewById<View>(R.id.article_author) as TextView
        progressBar = v.findViewById(R.id.video_progress_bar)
        muteIndicator = v.findViewById(R.id.mute_indicator)
        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator)
        articlePreviewLabel = v.findViewById(R.id.article_description)
        articleAuthorImage = v.findViewById(R.id.article_author_image)
        articleDateLabel = v.findViewById(R.id.article_date)
        cardView = v.findViewById(R.id.video_constraint_layout)
    }

    override fun getRootView(): View? {
        return layout
    }

    override fun getTextureView(): TextureView? {
        return textureView
    }

    override fun getPreviewImage(): ImageView? {
        return previewImage
    }

    override fun getPlayButton(): ImageView? {
        return playButton
    }

    override fun getRestartButton(): ImageView? {
        return restartButton
    }

    override fun getTitleLabel(): TextView? {
        return titleLabel
    }

    override fun getAuthorLabel(): TextView? {
        return authorLabel
    }

    override fun getPreviewTextLabel(): TextView? {
        return articlePreviewLabel
    }

    override fun getAuthorImageView(): ImageView? {
        return articleAuthorImage
    }

    override fun getDateLabel(): TextView? {
        return articleDateLabel
    }

    override fun displaySponsoredIndicators(isSponsored: Boolean) {
        if (cardView != null) {
            cardView!!.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun getMuteIndicator(): ImageView? {
        return muteIndicator
    }

    override fun getAdChoicesImageView(): ImageView? {
        return adChoicesIndicator
    }

    override fun formatDate(date: Date): String? {
        return null
    }

    override fun getProgressBar(): ProgressBar? {
        return progressBar
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

    override fun onVideoPlaybackError(videoPlaybackError: VideoPlaybackError) {
        Log.d(TAG, "onVideoPlaybackError: ")
    }
}