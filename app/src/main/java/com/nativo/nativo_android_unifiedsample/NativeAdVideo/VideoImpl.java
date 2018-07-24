package com.nativo.nativo_android_unifiedsample.NativeAdVideo;

import android.content.Context;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.R;

import net.nativo.sdk.ntvadtype.video.NtvVideoAdInterface;

import java.util.Date;

public class VideoImpl implements NtvVideoAdInterface {
    private RelativeLayout layout;
    private TextureView textureView;
    private ImageView previewImage;
    private ImageView playButton;
    private ImageView restartButton;
    private TextView titleLabel;
    private TextView authorLabel;
    private ImageView sponsoredIndicator;

    @Override
    public void bindViews(View v) {
        layout = (RelativeLayout) v.findViewById(R.id.video_layout);
        textureView = (TextureView) v.findViewById(R.id.video_texture);
        previewImage = (ImageView) v.findViewById(R.id.video_preview_image);
        playButton = (ImageView) v.findViewById(R.id.video_play);
        restartButton = (ImageView) v.findViewById(R.id.video_restart);
        titleLabel = (TextView) v.findViewById(R.id.video_title);
        authorLabel = (TextView) v.findViewById(R.id.video_author);
        sponsoredIndicator = (ImageView) v.findViewById(R.id.video_sponsored_indicator);
    }

    @Override
    public View getRootView() {
        return layout;
    }

    @Override
    public TextureView getTextureView() {
        return textureView;
    }

    @Override
    public ImageView getPreviewImage() {
        return previewImage;
    }

    @Override
    public ImageView getPlayButton() {
        return playButton;
    }

    @Override
    public ImageView getRestartButton() {
        return restartButton;
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
        return null;
    }

    @Override
    public ImageView getAuthorImageView() {
        return null;
    }

    @Override
    public TextView getDateLabel() {
        return null;
    }

    @Override
    public void displaySponsoredIndicators(boolean b) {
        sponsoredIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public String formatDate(Date date) {
        return null;
    }

    @Override
    public int getLayout(Context context) {
        return R.layout.video_layout;
    }

}
