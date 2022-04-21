package com.nativo.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.injector.landing.NtvLandingPageInjectable;
import java.util.Date;

public class SponsoredContentActivity extends AppCompatActivity implements NtvLandingPageInjectable {

    boolean withView = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NativoSDK.initLandingPage(this);
    }

    /**
     * NtvLandingPageInterface
     */
    private WebView webView;
    private TextView titleLabel;
    private TextView authorNameLabel;
    private ImageView articleAuthorImage;
    private ImageView shareButton;

    @Override
    public void bindViews(View v) {
        webView = v.findViewById(R.id.web_view);
        titleLabel = v.findViewById(R.id.title_label);
        authorNameLabel = v.findViewById(R.id.article_author);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
    }

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
        shareButton = (ImageView) findViewById(R.id.share_icon);
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
    public View getAdContainerView() {
        return findViewById(android.R.id.content).getRootView();
    }
}
