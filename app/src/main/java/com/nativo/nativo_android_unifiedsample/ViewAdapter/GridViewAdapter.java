package com.nativo.nativo_android_unifiedsample.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
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
import net.nativo.sdk.ntvconstant.NtvAdTypeConstants;
import net.nativo.sdk.ntvconstant.NtvConstants;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import static com.nativo.nativo_android_unifiedsample.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SECTION_URL;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.nativo_android_unifiedsample.util.AppConstants.SP_SECTION_URL;

public class GridViewAdapter extends BaseAdapter implements NtvSectionAdapter {

    private static String RSS_URL = "http://www.engadget.com/rss.xml";
    Context context;
    ViewGroup parent;

    public GridViewAdapter(Context context, ViewGroup parent) {
        this.context = context;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return 3;
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
        }
        if (NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, i).equals(NtvAdTypeConstants.AD_TYPE_VIDEO)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
        } else if (NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, i).equals(NtvAdTypeConstants.AD_TYPE_NATIVE)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
        }
        boolean ad = NativoSDK.getInstance().placeAdInView(view, parent, SECTION_URL, i, this, null);
        if (!ad) {
            bindView(view, i);
        }
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
        return true;
    }

    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        context.startActivity(new Intent(context, SponsoredContentActivity.class)
                .putExtra(SP_SECTION_URL, s)
                .putExtra(SP_CAMPAIGN_ID, i));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, int index, NtvAdData ntvAdData) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onFail(String s, int index) {

    }

    class ViewHolder {
        View container;
        ImageView articleImage;
        TextView articleTitle;
        TextView articleAuthor;
        ImageView articleSponsor;

        ViewHolder(@NonNull View container) {
            this.container = container;
            articleImage = container.findViewById(R.id.article_image);
            articleTitle = container.findViewById(R.id.article_title);
            articleAuthor = container.findViewById(R.id.article_author);
            articleSponsor = container.findViewById(R.id.video_sponsored_indicator);
        }
    }
}
