package com.nativo.sampleapp.NativeAdVideo

import android.content.Context
import net.nativo.sdk.ntvadtype.video.fullscreen.NtvFullscreenVideoInterface
import android.view.TextureView
import android.view.View
import android.widget.*
import com.nativo.sampleapp.R

class FullScreenVideoImpl : NtvFullscreenVideoInterface {
    private var textureView: TextureView? = null
    private var mediaControllerWrapper: FrameLayout? = null
    private var titleLabel: TextView? = null
    private var authorLabel: TextView? = null
    private var previewTextLabel: TextView? = null
    private var currentTimeView: TextView? = null
    private var durationTimeView: TextView? = null
    private var learnMoreButton: Button? = null
    private var shareButton: Button? = null
    private var seekBar: SeekBar? = null
    private var playPauseButton: ImageButton? = null
    private var exitFullScreenButton: ImageButton? = null
    private var moreInfoButton: ImageButton? = null
    private var adContainerView: View? = null
    private var progressBar: ProgressBar? = null

    override fun bindViews(v: View) {
        adContainerView = v
        textureView = v.findViewById<View>(R.id.video) as TextureView
        mediaControllerWrapper =
            v.findViewById<View>(R.id.full_screen_media_controller_wrapper) as FrameLayout
        titleLabel = v.findViewById<View>(R.id.title) as TextView
        authorLabel = v.findViewById<View>(R.id.author) as TextView
        previewTextLabel = v.findViewById<View>(R.id.description) as TextView
        learnMoreButton = v.findViewById<View>(R.id.learn_more) as Button
        shareButton = v.findViewById<View>(R.id.share) as Button
        seekBar = v.findViewById<View>(R.id.mediacontroller_progress) as SeekBar
        currentTimeView = v.findViewById<View>(R.id.current_time) as TextView
        durationTimeView = v.findViewById<View>(R.id.end_time) as TextView
        playPauseButton = v.findViewById<View>(R.id.play_pause) as ImageButton
        exitFullScreenButton = v.findViewById<View>(R.id.exit_fullscreen) as ImageButton
        moreInfoButton = v.findViewById<View>(R.id.more_info) as ImageButton
        progressBar = v.findViewById(R.id.video_progress_bar)
    }

    override fun getAdContainerView(): View? {
        return adContainerView
    }

    override fun getLayout(context: Context): Int {
        return R.layout.full_screen_custom_controls
    }

    override fun getLandscapeLayout(context: Context): Int {
        return R.layout.full_screen_custom_controls_landscape
    }

    override fun getTextureView(): TextureView? {
        return textureView
    }

    override fun getMediaControllerWrapper(): FrameLayout? {
        return mediaControllerWrapper
    }

    override fun getTitleLabel(): TextView? {
        return titleLabel
    }

    override fun getAuthorLabel(): TextView? {
        return authorLabel
    }

    override fun getPreviewTextLabel(): TextView? {
        return previewTextLabel
    }

    override fun getLearnMoreButton(): Button? {
        return learnMoreButton
    }

    override fun getShareButton(): Button? {
        return shareButton
    }

    override fun getSeekBar(): SeekBar? {
        return seekBar
    }

    override fun getCurrentTimeView(): TextView? {
        return currentTimeView
    }

    override fun getDurationTimeView(): TextView? {
        return durationTimeView
    }

    override fun getPlayPauseButton(): ImageButton? {
        return playPauseButton
    }

    override fun getExitButton(): ImageButton? {
        return exitFullScreenButton
    }

    override fun getInfoButton(): ImageButton? {
        return moreInfoButton
    }

    override fun getProgressBar(): ProgressBar? {
        return progressBar
    }
}