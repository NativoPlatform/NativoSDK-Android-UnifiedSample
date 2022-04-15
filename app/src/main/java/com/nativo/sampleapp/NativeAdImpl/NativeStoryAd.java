package com.nativo.sampleapp.NativeAdImpl;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.nativo.sampleapp.R;
import net.nativo.sdk.injector.nativead.NtvNativeAdInjectable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NativeStoryAd implements NtvNativeAdInjectable {

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
    private View adContainerView;

    @Override
    public ImageView getPreviewImageView() {
        return image;
    }

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
        if (!authorLabel.getText().toString().contains("By")) {
            authorLabel.append("By ", 0, 3);
        }
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
    public void displaySponsoredIndicators(boolean b) {
        if (cardView != null) {
            cardView.setBackgroundColor(Color.LTGRAY);
        } else if (view != null){
            view.setBackgroundColor(Color.LTGRAY);
        }
        if (sponsoredTag != null) {
            sponsoredTag.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ImageView getAdChoicesImageView() {
        return adChoicesIndicator;
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
        articlePreviewLabel = v.findViewById(R.id.article_description);
        articleAuthorImage = v.findViewById(R.id.article_author_image);
        sponsoredTag = v.findViewById(R.id.sponsored_tag);
        adChoicesIndicator = v.findViewById(R.id.adchoices_indicator);
    }

    @Override
    public View getAdContainerView() {
        return adContainerView;
    }

}
