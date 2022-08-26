package com.nativo.sampleapp.ViewAdapter;

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
import androidx.tracing.Trace;

import com.nativo.sampleapp.NativeAdImpl.NativeAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.NativeStoryAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAdRecycler;
import com.nativo.sampleapp.R;
import com.nativo.sampleapp.SponsoredContentActivity;
import com.nativo.sampleapp.ViewHolders.RecyclerListViewHolder;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvadtype.NtvBaseInterface;
import net.nativo.sdk.ntvconstant.NativoAdType;
import net.nativo.sdk.ntvcore.NtvAdData;
import net.nativo.sdk.ntvcore.NtvSectionAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL;
import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;
import static com.nativo.sampleapp.util.AppConstants.SP_CAMPAIGN_ID;
import static com.nativo.sampleapp.util.AppConstants.SP_CONTAINER_HASH;
import static com.nativo.sampleapp.util.AppConstants.SP_SECTION_URL;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerListViewHolder> implements NtvSectionAdapter {

    private static String TAG = RecyclerViewAdapter.class.getName();
    private Context context;
    private RecyclerView recyclerView;
    private final int listSize = 15;
    List<Integer> integerList = new ArrayList<>();
    private List<Map<String, String>> adRequestOptions = new ArrayList<>(listSize);
    private int adRequestCount = 0;

    // Helper method to determine which indexes should be Nativo ads
    public boolean shouldPlaceNativoAdAtIndex(int i) {
        return i % 2 == 1;
    }

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        for (int i = 0; i < listSize; i++) {
            integerList.add(i);

            /*
             * Benchmark rule
             * For each request, request a new ad type
             */
            if (shouldPlaceNativoAdAtIndex(i)) {
                HashMap<String, String> options = new HashMap<>();
                options.put("ntv_pl", "242444");
                switch (adRequestCount) {
                    case 0: options.put("ntv_a", "168639"); break; // Native
                    case 1: options.put("ntv_a", "168646"); break; // Click Video
                    case 2: options.put("ntv_a", "180314"); break; // Scroll Video
                    case 3:                                        // No fill
                        options.clear();
                        options.put("ntv_tp", "1");
                        break;
                    case 4: options.put("ntv_a", "168641"); break; // Display
                    case 5: options.put("ntv_a", "350180"); break; // Story
                    case 6: options.put("ntv_a", "1092616"); break; // Std Display
                    default: options.put("ntv_tp", "1"); break;
                }
                adRequestCount++;
                adRequestOptions.add(options);
            } else {
                adRequestOptions.add(null);
            }
        }
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
            Map<String, String> options = null;
            try {
                options = adRequestOptions.get(i);
            } catch (Exception ignored){}
            isAdContentAvailable = NativoSDK.placeAdInView(view, recyclerView, SECTION_URL, i, this, options);
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

        /*
         * Trace complete at end of list
         */
        if (i == integerList.size()-1) {
            Trace.endAsyncSection("scrollBenchmark",1);
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

    /**
     * NtvSectionAdapter implementation
     */
    @Override
    public Class<?> registerLayoutClassForIndex(int i, NtvAdData.NtvAdTemplateType ntvAdTemplateType) {
        return null;
    }

    @Override
    public void needsDisplayLandingPage(String s, int i) {
        context.startActivity(new Intent(context, SponsoredContentActivity.class)
                .putExtra(SP_SECTION_URL, s)
                .putExtra(SP_CAMPAIGN_ID, i)
                .putExtra(SP_CONTAINER_HASH, recyclerView.hashCode()));
    }

    @Override
    public void needsDisplayClickOutURL(String s, String s1) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s1)));
    }

    @Override
    public void hasbuiltView(View view, NtvBaseInterface ntvBaseInterface, NtvAdData ntvAdData) {

    }

    public void onReceiveAd(String section, NtvAdData ntvAdData, Integer index) {
        Log.e(this.getClass().getName(), "Index: "+index+" Did receive ad: "+ ntvAdData);
        notifyDataSetChanged();
    }

    @Override
    public void onFail(String section, Integer index) {
        // Remove failed Nativo ads
        NativoAdType adTypeForIndex = NativoSDK.getAdTypeForIndex(SECTION_URL, recyclerView, index);
        if (adTypeForIndex == NativoAdType.AD_TYPE_NONE) {
            integerList.remove(index);
            notifyItemRemoved(index);
            notifyItemChanged(index);
        }
        notifyDataSetChanged();
    }
}
