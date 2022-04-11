package com.nativo.sampleapp.NativeAdLandingImpl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.sampleapp.R;

import net.nativo.sdk.adtype.landing.NtvLandingPageInterface;

import java.util.Date;

public class NativeLandingPage implements NtvLandingPageInterface {

    private WebView webView;
    private TextView titleLabel;
    private TextView authorNameLabel;
    private View adContainerView;
    private ImageView articleAuthorImage;
    private int boapIndex = 0;
    private ImageView shareButton;
    private ViewGroup scrollView;

    @Override
    public WebView getContentWebView() {
        return webView;
    }

    @Override
    public TextView getTitleLabel() {
        return titleLabel;
    }

    @Override
    public TextView getAuthorNameLabel() {
        return authorNameLabel;
    }

    @Override
    public ImageView getAuthorImageView() {
        return articleAuthorImage;
    }

    @Override
    public ImageView getPreviewImageView() {
        return null;
    }

    @Override
    public TextView getPreviewTextLabel() {
        return null;
    }

    @Override
    public TextView getDateLabel() {
        return null;
    }

    @Override
    public String formatDate(Date date) {
        return null;
    }

    @Override
    public boolean contentWebViewShouldScroll() {
        return false;
    }

    @Override
    public void contentWebViewOnPageFinished() {
    }

    @Override
    public void contentWebViewOnReceivedError(String s) {

    }

    @Override
    public int getLayout(Context context) {
        return R.layout.activity_sponsored_content;
    }

    @Override
    public void setShareAndTrackingUrl(String shareUrl, String adUUID) {
        shareButton = (ImageView) adContainerView.findViewById(R.id.share_icon);
        if (shareButton != null) {
            shareButton.setOnClickListener(v -> {
                v.getContext().startActivity(Intent.createChooser(
                        new Intent(Intent.ACTION_SEND)
                                .setType("text/plain")
                                .putExtra(Intent.EXTRA_TEXT, shareUrl), "Share to..."));

                // TODO: track share action
                //NativoSDK.trackShareAction(adUUID);
            });
        }
    }

    @Override
    public void bindViews(View v) {
        adContainerView = v;
        webView = v.findViewById(R.id.web_view);
        titleLabel = v.findViewById(R.id.title_label);
        authorNameLabel = v.findViewById(R.id.article_author);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
        scrollView = adContainerView.findViewById(R.id.landing_boap_container);
    }

    @Override
    public View getAdContainerView() {
        return adContainerView;
    }
}
