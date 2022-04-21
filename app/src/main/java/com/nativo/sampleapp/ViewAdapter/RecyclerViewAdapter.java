package com.nativo.sampleapp.ViewAdapter;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nativo.sampleapp.NativeAdImpl.NativeAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAdRecycler;
import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvAdData;
import net.nativo.sdk.NtvAdTemplateType;
import net.nativo.sdk.NtvNotificationAdapter;
import net.nativo.sdk.constant.NativoAdType;
import net.nativo.sdk.injector.NtvInjectable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerListViewHolder> implements NtvNotificationAdapter {

    private static String TAG = RecyclerViewAdapter.class.getName();
    private Context context;
    private RecyclerView recyclerView;
    List<Integer> integerList = new ArrayList<>();
    Set<Integer> adsRequestIndex = new HashSet<>();


    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        // Nativo init
        NativoSDK.setNotificationAdapterForSection(SECTION_URL, this, context);

        this.context = context;
        this.recyclerView = recyclerView;
        for (int i = 0; i < 20; i++) {
            integerList.add(i);
        }
    }

    // Helper method to determine which indexes should be Nativo ads
    public boolean shouldPlaceNativoAdAtIndex(int i) {
        return i % 2 == 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (shouldPlaceNativoAdAtIndex(position)) {
            NativoAdType adType = NativoSDK.getAdTypeForIndex(SECTION_URL, recyclerView, position);
            switch (adType) {
                case AD_TYPE_NATIVE: return 1;
                case AD_TYPE_VIDEO: return 2;
                case AD_TYPE_STANDARD_DISPLAY: return 3;
                default: return 0; // Publisher item view, in case of no ad fill
            }
        } else {
            // Publisher item view
            return 0;
        }
    }

    @Override
    public RecyclerListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewTypeInt) {
        RecyclerListViewHolder viewHolder;
        View adViewTry;
        // TODO: Inflate empty view for Nativo ads. No need to check ad type.
        // Then inflate previously registered layout and inject into view.
        if (viewTypeInt == 1) { // Nativo Article
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_article, viewGroup, false);
            viewHolder = new NativeAdRecycler(adViewTry, viewGroup);
        } else if (viewTypeInt == 2) { // Nativo Video
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_layout, viewGroup, false);
            viewHolder = new NativeVideoAdRecycler(adViewTry, viewGroup);
        } else if (viewTypeInt == 3) { // Nativo Banner Ad
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.standard_display, viewGroup, false);
            viewHolder = new StandardDisplayAdRecycler(adViewTry, viewGroup);
        } else { // Publisher Article Layout
            adViewTry = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publisher_article, viewGroup, false);
            viewHolder = new RecyclerListViewHolder(adViewTry, viewGroup);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListViewHolder listViewHolder, int i) {

        View view = listViewHolder.getContainer();
        boolean isAdContentAvailable = false;
        if (shouldPlaceNativoAdAtIndex(i)) {
            Log.e(TAG, "binding index: "+i);
            adsRequestIndex.add(i);
            isAdContentAvailable = NativoSDK.placeAdInView(view, recyclerView, SECTION_URL, i, null);
        }

        if (!isAdContentAvailable) {
            bindView(listViewHolder.getContainer(), i);
        }
    }

    private void bindView(View view, int i) {
        if (view != null) {
            if (view.findViewById(R.id.article_image) != null) {
                ((ImageView) view.findViewById(R.id.article_image)).setImageResource(R.drawable.newsimage);
            }
            if (view.findViewById(R.id.sponsored_ad_indicator) != null) {
                view.findViewById(R.id.sponsored_ad_indicator).setVisibility(View.INVISIBLE);
            }
            if (view.findViewById(R.id.article_author) != null) {
                ((TextView) view.findViewById(R.id.article_author)).setText(R.string.sample_author);
            }
            if (view.findViewById(R.id.article_title) != null) {
                ((TextView) view.findViewById(R.id.article_title)).setText(R.string.sample_title);
            }
            if (view.findViewById(R.id.article_description) != null) {
                ((TextView) view.findViewById(R.id.article_description)).setText(R.string.sample_description);
            }
            if (view.findViewById(R.id.sponsored_tag) != null) {
                view.findViewById(R.id.sponsored_tag).setVisibility(View.INVISIBLE);
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
        return integerList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerListViewHolder holder) {
        if (holder instanceof NativeVideoAdRecycler) {
            TextureView textureView = ((NativeVideoAdRecycler) holder).getTextureView();
            ((ViewGroup) holder.itemView).removeView(textureView);
        }
        super.onViewRecycled(holder);
    }

    @Override
    public void needsDisplayLandingPage(String sectionUrl, Intent landingPageIntent) {
        context.startActivity(landingPageIntent);
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvInjectable ntvInjectable, NtvAdData ntvAdData) {

    }

    @Override
    public void onReceiveAd(String sectionUrl, NtvAdData ntvAdData, Integer index) {
        Log.e(this.getClass().getName(), "Index: "+index+" Did receive ad: "+ ntvAdData);
        integerList.add(index);
        notifyDataSetChanged();
    }

    @Override
    public void onFail(String sectionUrl, Integer index) {
        // Remove failed Nativo ads
        NativoAdType adTypeForIndex = NativoSDK.getAdTypeForIndex(SECTION_URL, recyclerView, index);
        if (adTypeForIndex == NativoAdType.AD_TYPE_NONE) {
            integerList.remove(index);
            notifyItemRemoved(index);
            notifyItemChanged(index);
        }
        notifyDataSetChanged();
    }

    @Override
    public Class<NtvInjectable> registerInjectableClassForTemplateType(NtvAdTemplateType templateType, String sectionUrl, Integer index) {
        return null;
    }

}
