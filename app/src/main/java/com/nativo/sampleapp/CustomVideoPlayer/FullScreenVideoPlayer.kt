package com.nativo.sampleapp.CustomVideoPlayer

import android.content.Context
import android.view.TextureView
import android.view.View
import android.widget.*
import com.nativo.sampleapp.R
import net.nativo.sdk.injectable.NtvFullscreenVideoInjectable

class FullScreenVideoPlayer : NtvFullscreenVideoInjectable {

    private var _textureView: TextureView? = null
    private var _mediaControllerWrapper: FrameLayout? = null
    private var _titleLabel: TextView? = null
    private var _authorLabel: TextView? = null
    private var _previewTextLabel: TextView? = null
    private var _currentTimeView: TextView? = null
    private var _durationTimeView: TextView? = null
    private var _learnMoreButton: Button? = null
    private var _shareButton: Button? = null
    private var _seekBar: SeekBar? = null
    private var _playPauseButton: ImageButton? = null
    private var _exitFullScreenButton: ImageButton? = null
    private var _moreInfoButton: ImageButton? = null
    private lateinit var _view: View
    private var _progressBar: ProgressBar? = null

    override val textureView: TextureView?
        get() = _textureView
    override val mediaControllerWrapper: FrameLayout?
        get() = _mediaControllerWrapper
    override val titleLabel: TextView?
        get() = _titleLabel
    override val authorLabel: TextView?
        get() = _authorLabel
    override val previewTextLabel: TextView?
        get() = _previewTextLabel
    override val currentTimeView: TextView?
        get() = _currentTimeView
    override val durationTimeView: TextView?
        get() = _durationTimeView
    override val exitButton: ImageButton?
        get() = _exitFullScreenButton
    override val infoButton: ImageButton?
        get() = _moreInfoButton
    override val learnMoreButton: Button?
        get() = _learnMoreButton
    override val shareButton: Button?
        get() = _shareButton
    override val seekBar: SeekBar?
        get() = _seekBar
    override val playPauseButton: ImageButton?
        get() = _playPauseButton
    override val view: View
        get() = _view
    override val progressBar: ProgressBar?
        get() = _progressBar

    override fun bindViews(v: View) {
        _view = v
        _textureView = v.findViewById(R.id.video)
        _mediaControllerWrapper = v.findViewById(R.id.full_screen_media_controller_wrapper)
        _titleLabel = v.findViewById(R.id.title)
        _authorLabel = v.findViewById(R.id.author)
        _previewTextLabel = v.findViewById(R.id.description)
        _learnMoreButton = v.findViewById(R.id.learn_more)
        _shareButton = v.findViewById(R.id.share)
        _seekBar = v.findViewById(R.id.mediacontroller_progress)
        _currentTimeView = v.findViewById(R.id.current_time)
        _durationTimeView = v.findViewById(R.id.end_time)
        _playPauseButton = v.findViewById(R.id.play_pause)
        _exitFullScreenButton = v.findViewById(R.id.exit_fullscreen)
        _moreInfoButton = v.findViewById(R.id.more_info)
        _progressBar = v.findViewById(R.id.video_progress_bar)
    }

    override fun getLandscapeLayout(context: Context?): Int {
        return R.layout.full_screen_custom_controls_landscape
    }

    override fun getLayout(context: Context): Int {
        return R.layout.full_screen_custom_controls
    }
}