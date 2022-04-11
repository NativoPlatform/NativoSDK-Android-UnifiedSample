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

import com.nativo.sampleapp.NativeAdLandingImpl.NativeLandingPage;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvIntent;
import net.nativo.sdk.adtype.landing.NtvLandingPageInterface;

import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER;
import static com.nativo.sampleapp.util.AppConstants.SP_SECTION_URL;

import java.util.Date;

public class SponsoredContentActivity extends AppCompatActivity implements NtvLandingPageInterface {

    boolean withView = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NativoSDK.initLandingPage(this);

//        setContentView(R.layout.activity_sponsored_content);
//        View view = findViewById(R.id.landing_page_container);
//        String sectionUrl = getIntent().getStringExtra(SP_SECTION_URL);
//        int campaignId = getIntent().getIntExtra(SP_CAMPAIGN_ID, 0);
//        Integer containerHash = getIntent().getIntExtra(SP_CONTAINER, 0);
//        if (withView) {
//            NativoSDK.initLandingPage(view, sectionUrl, containerHash, campaignId, NativeLandingPage.class);
//        } else {
//            NativoSDK.initLandingPage(this, sectionUrl, containerHash, campaignId, NativeLandingPage.class);
//            NativoSDK.initLandingPage(this, sectionUrl,0,null, NativeLandingPage.class);
//        }

    }

    /**
     * NtvLandingPageInterface
     */
    private WebView webView;
    private TextView titleLabel;
    private TextView authorNameLabel;
    private View adContainerView;
    private ImageView articleAuthorImage;
    private ImageView shareButton;
    private ViewGroup scrollView;

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
    public View getAdContainerView() {
        return adContainerView;
    }
}
