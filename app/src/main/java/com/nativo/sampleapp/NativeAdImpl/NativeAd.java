package com.nativo.sampleapp.NativeAdImpl;

import android.content.Context;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nativo.sampleapp.R;

import net.nativo.sdk.injectable.NtvNativeAdInjectable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NativeAd implements NtvNativeAdInjectable {

    private LinearLayout layout;
    private CardView cardView;
    private TextView titleLabel;
    private TextView authorLabel;
    private TextView articlePreviewLabel;
    private TextView articleDateLabel;
    private ImageView articleAuthorImage;
    private ImageView image;
    private ImageView adChoicesIndicator;
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
    public View getView() {
        return view;
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
        layout = v.findViewById(R.id.article_layout);
        cardView = v.findViewById(R.id.article_constraint_layout);
        titleLabel = v.findViewById(R.id.article_title);
        authorLabel = v.findViewById(R.id.article_author);
        image = v.findViewById(R.id.article_image);
        articleDateLabel = v.findViewById(R.id.article_date);
        articlePreviewLabel = v.findViewById(R.id.article_description);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
        sponsoredTag = v.findViewById(R.id.sponsored_tag);
        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator);
    }

    @Override
    public ImageView getAdChoicesImageView() {
        return adChoicesIndicator;
    }

    @Override
    public boolean getShouldPrependAuthorByline() {
        return true;
    }
}
