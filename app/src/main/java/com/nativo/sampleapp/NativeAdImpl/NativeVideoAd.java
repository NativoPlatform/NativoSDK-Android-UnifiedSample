package com.nativo.sampleapp.NativeAdImpl;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.nativo.sampleapp.R;

import net.nativo.sdk.injectable.NtvVideoAdInjectable;
import net.nativo.sdk.video.VideoPlaybackError;

import java.util.Date;

public class NativeVideoAd implements NtvVideoAdInjectable {

    private static String TAG = NativeVideoAd.class.getName();
    private RelativeLayout layout;
    private TextureView textureView;
    private ImageView previewImage;
    private ImageView playButton;
    private ImageView restartButton;
    private ImageView muteIndicator;
    private TextView titleLabel;
    private TextView authorLabel;
    private ImageView sponsoredIndicator;
    private ImageView adChoicesIndicator;
    private ProgressBar progressBar;
    private View view;
    private TextView articlePreviewLabel;
    private ImageView articleAuthorImage;
    private TextView articleDateLabel;
    private CardView cardView;


    @Override
    public int getLayout(Context context) {
        return R.layout.video_layout;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void bindViews(View v) {
        view = v;
        textureView = (TextureView) v.findViewById(R.id.video);
        previewImage = (ImageView) v.findViewById(R.id.preview_image);
        playButton = (ImageView) v.findViewById(R.id.play);
        restartButton = (ImageView) v.findViewById(R.id.restart);
        titleLabel = (TextView) v.findViewById(R.id.article_title);
        authorLabel = (TextView) v.findViewById(R.id.article_author);
        progressBar = v.findViewById(R.id.video_progress_bar);
        muteIndicator = v.findViewById(R.id.mute_indicator);
        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator);
        articlePreviewLabel = v.findViewById(R.id.article_description);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
        articleDateLabel = v.findViewById(R.id.article_date);
        cardView = v.findViewById(R.id.video_constraint_layout);
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
        return articlePreviewLabel;
    }

    @Override
    public ImageView getAuthorImageView() {
        return articleAuthorImage;
    }

    @Override
    public TextView getDateLabel() {
        return articleDateLabel;
    }

    @Override
    public void displaySponsoredIndicators(boolean isSponsored) {
        if (cardView != null) {
            cardView.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public ImageView getMuteIndicator() {
        return muteIndicator;
    }

    @Override
    public ImageView getAdChoicesImageView() {
        return adChoicesIndicator;
    }

    @Override
    public String formatDate(Date date) {
        return null;
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void onVideoEnteredFullscreen() {
        Log.d(TAG, "onVideoEnteredFullscreen: ");
    }

    @Override
    public void onVideoExitedFullscreen() {
        Log.d(TAG, "onVideoExitedFullscreen: ");
    }

    @Override
    public void onVideoPlay() {
        Log.d(TAG, "onVideoPlay: ");
    }

    @Override
    public void onVideoPause() {
        Log.d(TAG, "onVideoPause: ");
    }

    @Override
    public void onVideoPlaybackCompleted() {
        Log.d(TAG, "onVideoPlaybackCompleted: ");
    }

    @Override
    public void onVideoPlaybackError(VideoPlaybackError videoPlaybackError) {
        Log.e("NativoSDK", "onVideoPlaybackError: "+ videoPlaybackError);
    }
}
