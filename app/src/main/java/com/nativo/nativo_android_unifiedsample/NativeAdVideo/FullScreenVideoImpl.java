package com.nativo.nativo_android_unifiedsample.NativeAdVideo;

import android.content.Context;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.R;

import net.nativo.sdk.ntvadtype.video.fullscreen.NtvFullscreenVideoInterface;

public class FullScreenVideoImpl implements NtvFullscreenVideoInterface {
    private TextureView textureView;
    private FrameLayout mediaControllerWrapper;
    private TextView titleLabel;
    private TextView authorLabel;
    private TextView previewTextLabel;
    private TextView currentTimeView;
    private TextView durationTimeView;
    private Button learnMoreButton;
    private Button shareButton;
    private SeekBar seekBar;
    private ImageButton playPauseButton;
    private ImageButton exitFullScreenButton;
    private ImageButton moreInfoButton;


    @Override
    public void bindViews(View v) {
        textureView = (TextureView) v.findViewById(R.id.video_texture);
        mediaControllerWrapper = (FrameLayout) v.findViewById(R.id.full_screen_media_controller_wrapper);
        titleLabel = (TextView) v.findViewById(R.id.video_title);
        authorLabel = (TextView) v.findViewById(R.id.video_author);
        previewTextLabel = (TextView) v.findViewById(R.id.description);
        learnMoreButton = (Button) v.findViewById(R.id.learn_more);
        shareButton = (Button) v.findViewById(R.id.share);
        seekBar = (SeekBar) v.findViewById(R.id.mediacontroller_progress);
        currentTimeView = (TextView) v.findViewById(R.id.current_time);
        durationTimeView = (TextView) v.findViewById(R.id.end_time);
        playPauseButton = (ImageButton) v.findViewById(R.id.play_pause);
        exitFullScreenButton = (ImageButton) v.findViewById(R.id.exit_fullscreen);
        moreInfoButton = (ImageButton) v.findViewById(R.id.more_info);

    }

    @Override
    public int getLayout(Context context) {
        return R.layout.full_screen_custom_controls;
    }

    @Override
    public int getLandscapeLayout(Context context) {
        return R.layout.full_screen_custom_controls_landscape;
    }

    @Override
    public TextureView getTextureView() {
        return textureView;
    }

    @Override
    public FrameLayout getMediaControllerWrapper() {
        return mediaControllerWrapper;
    }

    @Override
    public TextView getTitleLabel() {
        return titleLabel;
    }

    @Override
    public TextView getAuthorLabel() {
        return authorLabel;
    }

    @Override
    public TextView getPreviewTextLabel() {
        return previewTextLabel;
    }

    @Override
    public Button getLearnMoreButton() {
        return learnMoreButton;
    }

    @Override
    public Button getShareButton() {
        return shareButton;
    }

    @Override
    public SeekBar getSeekBar() {
        return seekBar;
    }

    @Override
    public TextView getCurrentTimeView() {
        return currentTimeView;
    }

    @Override
    public TextView getDurationTimeView() {
        return durationTimeView;
    }

    @Override
    public ImageButton getPlayPauseButton() {
        return playPauseButton;
    }

    @Override
    public ImageButton getExitButton() {
        return exitFullScreenButton;
    }

    @Override
    public ImageButton getInfoButton() {
        return moreInfoButton;
    }

}
