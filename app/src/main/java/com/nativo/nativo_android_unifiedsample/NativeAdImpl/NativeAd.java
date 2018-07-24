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
    private ImageView image;
    private ImageView sponsoredIndicator;
    private TextView sponsoredTag;
    private View view;

    @Override
    public TextView getTitleLabel() {
        if (titleLabel == null) {
            titleLabel = (TextView) view.findViewById(R.id.article_title);
            return titleLabel;
        }
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
    public ImageView getPreviewImageView() {
        return image;
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
        if (cardView != null) {
            cardView.setBackgroundColor(Color.LTGRAY);
        } else if (view != null){
            view.setBackgroundColor(Color.LTGRAY);
        }
        sponsoredIndicator.setVisibility(View.VISIBLE);
        sponsoredTag.setVisibility(View.VISIBLE);
    }

    @Override
    public String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date);
    }

    @Override
    public int getLayout(Context context) {
        return R.layout.article;
    }

    @Override
    public void bindViews(View v) {
        view = v;
        layout = v.findViewById(R.id.article_layout);
        cardView = v.findViewById(R.id.article_constraint_layout);
        titleLabel = v.findViewById(R.id.article_title);
        authorLabel = v.findViewById(R.id.article_author);
        image = v.findViewById(R.id.article_image);
        sponsoredIndicator = v.findViewById(R.id.video_sponsored_indicator);
        sponsoredTag = v.findViewById(R.id.sponsored_tag);
    }
}
