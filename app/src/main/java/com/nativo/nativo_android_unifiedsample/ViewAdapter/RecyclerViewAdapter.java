package com.nativo.nativo_android_unifiedsample.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nativo.nativo_android_unifiedsample.R;
import com.nativo.nativo_android_unifiedsample.SponsoredContentActivity;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder> implements NtvSectionAdapter {

    private static String SECTION_URL = "http://www.nativo.net/test/";
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
            articleSponsor = container.findViewById(R.id.sponsored_indicator);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article, viewGroup, false);
        return new ListViewHolder(adViewTry, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        boolean ad = NativoSDK.getInstance().placeAdInView(listViewHolder.container, recyclerView, SECTION_URL, i, this, null);
        if (!ad) {
            bindView(listViewHolder.container);
        }
    }

    private void bindView(View view) {
        if (view != null) {
            if (((ImageView) view.findViewById(R.id.article_image)) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (((ImageView) view.findViewById(R.id.sponsored_indicator)) != null) {
                ((ImageView) view.findViewById(R.id.sponsored_indicator)).setVisibility(View.INVISIBLE);
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
            view.findViewById(R.id.article_constraint_layout).setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
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
        context.startActivity(new Intent(context, SponsoredContentActivity.class)
                .putExtra(SponsoredContentActivity.SECTION_URL, s)
                .putExtra(SponsoredContentActivity.CAMPAIGN_ID, i));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {

    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String s, NtvAdData ntvAdData) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFail(String s, Exception e) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
