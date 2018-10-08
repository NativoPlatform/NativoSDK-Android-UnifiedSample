package com.nativo.nativo_android_unifiedsample.NativeAdImpl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.R;

import net.nativo.sdk.ntvadtype.nativead.NtvNativeAdInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NativeAd implements NtvNativeAdInterface {

    private LinearLayout layout;
    private CardView cardView;
    private TextView titleLabel;
    private TextView authorLabel;
    private TextView articlePreviewLabel;
    private TextView articleDateLabel;
    private ImageView articleAuthorImage;
    private ImageView image;
    private ImageView sponsoredIndicator;
    private TextView sponsoredTag;
    private View view;
    private View adContainerView;

    @Override
    public TextView getTitleLabel() {
        if (titleLabel == null) {
            titleLabel = (TextView) view.findViewById(R.id.article_title);
            return titleLabel;
        }
        return titleLabel;
    }

    @Override
    public View getAdContainerView() {
        return adContainerView;
    }

    @Override
    public TextView getAuthorLabel() {
        if(!authorLabel.getText().toString().contains("By")){
            authorLabel.append("By ",0,3);
        }
        return authorLabel;
    }

    @Override
    public TextView getPreviewTextLabel() {
        return articlePreviewLabel;
    }

    @Override
    public ImageView getPreviewImageView() {
        return image;
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
    public void displaySponsoredIndicators(boolean b) {
        if (cardView != null) {
            cardView.setBackgroundColor(Color.LTGRAY);
        }
        if (sponsoredIndicator != null) {
            sponsoredIndicator.setVisibility(View.VISIBLE);
        }
        if (sponsoredTag != null) {
            sponsoredTag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date);
    }

    @Override
    public int getLayout(Context context) {
        return R.layout.native_article;
    }

    @Override
    public void bindViews(View v) {
        view = v;
        adContainerView = v;
        layout = v.findViewById(R.id.article_layout);
        cardView = v.findViewById(R.id.article_constraint_layout);
        titleLabel = v.findViewById(R.id.article_title);
        authorLabel = v.findViewById(R.id.article_author);
        image = v.findViewById(R.id.article_image);
        articleDateLabel = v.findViewById(R.id.article_date);
        articlePreviewLabel = v.findViewById(R.id.article_preview);
        sponsoredIndicator = v.findViewById(R.id.sponsored_ad_indicator);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
        sponsoredTag = v.findViewById(R.id.sponsored_tag);
    }
}
