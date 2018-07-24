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

import com.nativo.nativo_android_unifiedsample.R;
import com.nativo.nativo_android_unifiedsample.SponsoredContentActivity;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

public class TableViewAdapter extends BaseAdapter implements NtvSectionAdapter {

    private static String SECTION_URL = "http://www.nativo.net/test/";
    private ViewGroup parent;

    public TableViewAdapter(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return 1;
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
//        if (view == null) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
//        }
        boolean ad = NativoSDK.getInstance().placeAdInView(view, viewGroup, SECTION_URL, i, this, null);
//        if (!ad) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
//            bindView(view, i);
//        }
        return view;
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (((ImageView) view.findViewById(R.id.article_image)) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (((ImageView) view.findViewById(R.id.video_sponsored_indicator)) != null) {
                ((ImageView) view.findViewById(R.id.video_sponsored_indicator)).setVisibility(View.INVISIBLE);
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
        }
    }

    @Override
    public boolean shouldPlaceAdAtIndex(String s, int i) {
        return true;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsReloadDataSource(String s, int i) {

    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        parent.getContext().startActivity(new Intent(parent.getContext(), SponsoredContentActivity.class)
                .putExtra(SponsoredContentActivity.SECTION_URL, s)
                .putExtra(SponsoredContentActivity.CAMPAIGN_ID, i));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        parent.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, NtvAdData ntvAdData) {

    }

    @Override
    public void onFail(String s, Exception e) {

    }

}
