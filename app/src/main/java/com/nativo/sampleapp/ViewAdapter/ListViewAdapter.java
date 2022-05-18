package com.nativo.sampleapp.ViewAdapter;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvAdData;
import net.nativo.sdk.NtvSectionAdapter;
import net.nativo.sdk.injectable.NtvInjectable;
import net.nativo.sdk.injectable.NtvInjectableType;

/**
 * Example of Nativo SDK implemented using ListView
 * Ads are placed according to rule in link{@code shouldPlaceAdAtIndex()}.
 * If an ad is not placed(eg no fill scenario) the cell is marked with red
 */
public class ListViewAdapter extends BaseAdapter /*implements NtvSectionAdapter*/ {

    private ViewGroup listView;

    public ListViewAdapter(ViewGroup parent) {
        this.listView = parent;
        //NativoSDK.initSectionWithAdapter(this, SECTION_URL, parent.getContext());
    }

    @Override
    public int getCount() {
        return 20;
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
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publisher_article, viewGroup, false);
        }
        if (NativoSDK.getAdTypeForIndex(SECTION_URL, listView, i).equals(NtvInjectableType.VIDEO)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
        } else if (NativoSDK.getAdTypeForIndex(SECTION_URL, listView, i).equals(NtvInjectableType.NATIVE)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
        }

        boolean ad = NativoSDK.placeAdInView(view, listView, SECTION_URL, i, null);
        if (!ad) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
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
            if (((TextView) view.findViewById(R.id.sponsored_tag)) != null) {
                ((TextView) view.findViewById(R.id.sponsored_tag)).setVisibility(View.INVISIBLE);
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
        return i % 3 == 0;
    }
/*
    @Override
    public void needsDisplayLandingPage(String sectionUrl, Intent landingPageIntent) {
        listView.getContext().startActivity(landingPageIntent);
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        listView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    public void hasbuiltView(View view, NtvInjectable ntvInjectable, NtvAdData ntvAdData) {

    }

    public void onReceiveAd(String section, NtvAdData ntvAdData, Integer index) {
        notifyDataSetChanged();
    }

    public void onFail(String section, Integer index) {
        notifyDataSetChanged();
    }

    @Override
    public Class<NtvInjectable> registerInjectableClassForTemplateType(NtvInjectableType templateType, String sectionUrl, Integer index) {
        return null;
    }

    @Override
    public void needsPlaceAdInView(@NonNull String sectionUrl, int atLocation) {
        notifyDataSetChanged();
    }

    @Override
    public void didPlaceAdInView(@NonNull String sectionUrl, int atLocation, @NonNull NtvInjectable adInjectable) {

    }

    @Override
    public void didFailAd(@NonNull String sectionUrl, int atLocation, Throwable error) {
        notifyDataSetChanged();
    }

    @Override
    public void didPrefetchAd(@NonNull String sectionUrl, boolean didGetAd) {

    }

    @Override
    public void didAssignAdToLocation(@NonNull String sectionUrl, int location) {

    }*/
}
