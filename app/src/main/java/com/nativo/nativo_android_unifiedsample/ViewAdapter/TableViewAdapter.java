package com.nativo.nativo_android_unifiedsample.ViewAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.SponsoredContentActivity;
import com.nativo.sampleapp.R;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvconstant.NativoAdType;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SECTION_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_CONTAINER_HASH;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_SECTION_URL;

public class TableViewAdapter extends BaseAdapter implements NtvSectionAdapter {

    private ViewGroup listView;

    public TableViewAdapter(ViewGroup parent) {
        this.listView = parent;
    }

    @Override
    public int getCount() {
        return 4;
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
        NativoSDK.getInstance().prefetchAdForSection(SECTION_URL, listView, i, this, null);
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publisher_article, viewGroup, false);
        }
        if (NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, listView, i).equals(NativoAdType.AD_TYPE_VIDEO)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
        } else if (NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, listView, i).equals(NativoAdType.AD_TYPE_NATIVE)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
        }

        boolean ad = NativoSDK.getInstance().placeAdInView(view, listView, SECTION_URL, i, this, null);
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
            if (shouldPlaceAdAtIndex("sample", i)) {
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

    @Override
    public boolean shouldPlaceAdAtIndex(String s, int i) {
        return i==3;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        listView.getContext().startActivity(new Intent(listView.getContext(), SponsoredContentActivity.class)
                .putExtra(SP_SECTION_URL, s)
                .putExtra(SP_CAMPAIGN_ID, i)
                .putExtra(SP_CONTAINER_HASH, listView.hashCode()));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        listView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, NtvAdData ntvAdData) {

    }

    @Override
    public void onFail(String s) {

    }

}
