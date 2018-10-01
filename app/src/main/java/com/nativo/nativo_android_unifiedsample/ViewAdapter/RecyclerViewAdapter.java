package com.nativo.nativo_android_unifiedsample.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.NativeAdImpl.NativeAdRecycler;
import com.nativo.nativo_android_unifiedsample.NativeAdImpl.SingleVideoAdRecycler;
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


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NtvSectionAdapter {

    private Context context;
    private RecyclerView recyclerView;
    private static int x = 0;

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        View container;
        ViewGroup parent;
        ImageView articleImage;
        TextView articleTitle;
        TextView articleAuthor;
        ImageView articleSponsor;

        ListViewHolder(@NonNull View container, ViewGroup viewGroup) {
            super(container);
            this.container = container;
            parent = viewGroup;
            articleImage = container.findViewById(R.id.article_image);
            articleTitle = container.findViewById(R.id.article_title);
            articleAuthor = container.findViewById(R.id.article_author);
            articleSponsor = container.findViewById(R.id.video_sponsored_indicator);
        }

        public View getContainer() {
            return container;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View adViewTry;
        if (i == 1) {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
            viewHolder = new NativeAdRecycler(adViewTry);
        } else if (i == 2) {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
            viewHolder = new SingleVideoAdRecycler(adViewTry);
        } else {
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
            viewHolder = new ListViewHolder(adViewTry, viewGroup);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder listViewHolder, int i) {
        NativoSDK.getInstance().prefetchAdForSection(SECTION_URL, i, this, null);
        boolean ad = false;
        if (NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, i).equals(NtvAdTypeConstants.AD_TYPE_NATIVE)) {
            ad = NativoSDK.getInstance().placeAdInView(((NativeAdRecycler) listViewHolder), recyclerView, SECTION_URL, i, this, null);
        } else if (NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, i).equals(NtvAdTypeConstants.AD_TYPE_VIDEO)) {
            ad = NativoSDK.getInstance().placeAdInView(((SingleVideoAdRecycler) listViewHolder), recyclerView, SECTION_URL, i, this, null);
        }
        if (!ad) {
            bindView(((ListViewHolder) listViewHolder).container, i);
        }
    }


    @Override
    public int getItemViewType(int position) {
        String s = NativoSDK.getInstance().getAdTypeForIndex(SECTION_URL, position);
        switch (s) {
            case NtvAdTypeConstants.AD_TYPE_VIDEO:
                return 2;
            case NtvAdTypeConstants.AD_TYPE_NATIVE:
                return 1;
            case NtvAdTypeConstants.AD_TYPE_NONE:
                return 0;
            default:
                return -1;
        }
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
    public int getItemCount() {
        return 10;
    }


    @Override
    public boolean shouldPlaceAdAtIndex(String s, int i) {
        return i%2==0;
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
    }

    @Override
    public void onFail(String s, int index) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
