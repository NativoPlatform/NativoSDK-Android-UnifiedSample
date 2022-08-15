package com.nativo.sampleapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.injectable.NtvLandingPageInjectable;
import java.util.Date;

public class SponsoredContentActivity extends AppCompatActivity implements NtvLandingPageInjectable {

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
    private View view;

    @Override
    public void bindViews(View v) {
        view = v;
        webView = v.findViewById(R.id.web_view);
        titleLabel = v.findViewById(R.id.title_label);
        authorNameLabel = v.findViewById(R.id.article_author);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
    }

    @Override
    public void setShareUrl(String shareUrl) {
        ImageView shareButton = findViewById(R.id.share_icon);
        if (shareButton != null) {
            if (shareUrl != null) {
                shareButton.setOnClickListener(view -> {
                    view.getContext().startActivity(Intent.createChooser(
                            new Intent(Intent.ACTION_SEND)
                                    .setType("text/plain")
                                    .putExtra(Intent.EXTRA_TEXT, shareUrl), "Share to..."));
                    NativoSDK.trackShareAction(shareUrl);
                });
            } else {
                shareButton.setVisibility(View.GONE);
            }
        }
    }

    @NonNull
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


    @NonNull
    @Override
    public View getView() {
        return view;
    }

}
