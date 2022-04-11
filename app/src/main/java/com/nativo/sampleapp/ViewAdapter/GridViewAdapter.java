package com.nativo.sampleapp.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.sampleapp.SponsoredContentActivity;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvIntent;
import net.nativo.sdk.NtvNotificationAdapter;
import net.nativo.sdk.adtype.NtvBaseInterface;
import net.nativo.sdk.constant.NativoAdType;
import net.nativo.sdk.NtvAdData;

import java.util.ArrayList;
import java.util.List;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;
import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER;
import static com.nativo.sampleapp.util.AppConstants.SP_SECTION_URL;

public class GridViewAdapter extends BaseAdapter implements NtvNotificationAdapter {

    private Context context;
    private GridView gridView;
    List<Integer> integerList = new ArrayList<>();

    public GridViewAdapter(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }

        // Nativo init
        NativoSDK.setNotificationAdapterForSection(SECTION_URL, this, context);
    }

    @Override
    public int getCount() {
        return integerList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (shouldPlaceNativoAdAtIndex(i)) {
            NativoAdType adType = NativoSDK.getAdTypeForIndex(SECTION_URL, gridView, i);
            switch (adType) {
                case AD_TYPE_NATIVE:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
                case AD_TYPE_VIDEO:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
                case AD_TYPE_STANDARD_DISPLAY:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.standard_display, viewGroup, false);
                default:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
            }
            boolean isNativoAdAvailable = NativoSDK.placeAdInView(view, gridView, SECTION_URL, i, null);

            // Hide if ad could not be placed in view
            if (!isNativoAdAvailable) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            // Publisher article view
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publisher_article, viewGroup, false);
            bindView(view, i);
        }
        return view;
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (((ImageView) view.findViewById(R.id.article_image)) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (((ImageView) view.findViewById(R.id.sponsored_ad_indicator)) != null) {
                ((ImageView) view.findViewById(R.id.sponsored_ad_indicator)).setVisibility(View.INVISIBLE);
            }
            if (((TextView) view.findViewById(R.id.article_author)) != null) {
                ((TextView) view.findViewById(R.id.article_author)).setText(R.string.sample_author);
            }
            if (((TextView) view.findViewById(R.id.article_title)) != null) {
                ((TextView) view.findViewById(R.id.article_title)).setText(R.string.sample_title);
            }
            if (shouldPlaceNativoAdAtIndex(i)) {
                view.findViewById(R.id.article_constraint_layout).setBackgroundColor(Color.RED);
            } else {
                view.findViewById(R.id.article_constraint_layout).setBackgroundColor(Color.WHITE);
            }
            view.setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CLICK_OUT_URL)));
        }
    };

    public boolean shouldPlaceNativoAdAtIndex(int i) {
        return i % 2 == 1;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    public void needsDisplayLandingPage(String s, int i) {
        context.startActivity(new Intent(context, SponsoredContentActivity.class)
                .putExtra(SP_SECTION_URL, s)
                .putExtra(SP_CAMPAIGN_ID, i)
                .putExtra(SP_CONTAINER, gridView.hashCode()));
    }

    @Override
    public void needsDisplayLandingPage(String sectionUrl, NtvIntent landingPageIntent) {
        context.startActivity(landingPageIntent);
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String section, NtvAdData ntvAdData, Integer index) {
        notifyDataSetChanged();
    }

    @Override
    public void onFail(String section, Integer index) {
        notifyDataSetChanged();
    }
}
