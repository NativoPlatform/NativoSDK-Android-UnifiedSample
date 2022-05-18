package com.nativo.sampleapp.ViewAdapter;

import static com.nativo.sampleapp.util.AppConstants.SECTION_URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.nativo.sampleapp.NativeAdImpl.NativeAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAdRecycler;
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAdRecycler;
import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder;
import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NativoViewHolder;
import net.nativo.sdk.NtvAdData;
import net.nativo.sdk.NtvSectionAdapter;
import net.nativo.sdk.injectable.NtvInjectable;
import net.nativo.sdk.injectable.NtvInjectableType;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NtvSectionAdapter {

    private static String TAG = RecyclerViewAdapter.class.getName();
    final String NtvTAG = "NativoSDK";
    private Context context;
    private RecyclerView recyclerView;
    ArrayList<String> articleList = new ArrayList<>();

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        // Create sudo article list
        for (int i = 0; i < 20; i++) {
            String title = "Publisher Article " + i;
            articleList.add(title);
        }

        // Nativo init
        NativoSDK.initSectionWithAdapter(this, SECTION_URL, context);
    }

    // Helper method to determine which indexes should be Nativo ads
    public boolean shouldPlaceNativoAdAtIndex(int i) {
        return i % 3 == 1;
    }

    /**
    * LEGACY
    * */
/*    @Override
    public int getItemViewType(int position) {
        if (shouldPlaceNativoAdAtIndex(position)) {
            NtvInjectableType adType = NativoSDK.getAdTypeForIndex(SECTION_URL, recyclerView, position);
            if (adType != null) {
                switch (adType) {
                    case NATIVE: return 1;
                    case VIDEO: return 2;
                    case STANDARD_DISPLAY: return 3;
                }
            }
        }
        // Publisher item view
        return 0;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewTypeInt) {
        RecyclerView.ViewHolder viewHolder;
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
            viewHolder = new ArticleViewHolder(adViewTry, viewGroup);
        }
        return viewHolder;
    }*/

    // Item View Type constants
    final int ITEM_TYPE_ARTICLE = 0;
    final int ITEM_TYPE_NATIVO = 1;

    @Override
    public int getItemViewType(int position) {
        NtvInjectableType nativeType = null;
        if (shouldPlaceNativoAdAtIndex(position)) {
            //Log.d(NtvTAG, "shouldPlaceNativoAdAtIndex "+position);
            nativeType = NativoSDK.getAdTypeForIndex(SECTION_URL, recyclerView, position);
        }
        if (nativeType != null) {
            return ITEM_TYPE_NATIVO;
        } else {
            return ITEM_TYPE_ARTICLE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewTypeInt) {
        // TODO: Inflate empty view for Nativo ads. No need to check ad type. Then inflate previously registered layout and inject into view.
        if (viewTypeInt == ITEM_TYPE_ARTICLE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publisher_article, viewGroup, false);
            return new ArticleViewHolder(view, viewGroup);
        } else if (viewTypeInt == ITEM_TYPE_NATIVO) {
            Log.d(NtvTAG, "Created new NativoViewHolder");
            return new NativoViewHolder(viewGroup.getContext());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        boolean isAdContentAvailable = false;
        if (holder instanceof NativoViewHolder) {
            isAdContentAvailable = NativoSDK.placeAdInView(holder.itemView, recyclerView, SECTION_URL, position, null);
        }

        // LEGACY
/*        boolean isAdContentAvailable = false;
        if (holder instanceof NtvInjectable) {
            NtvInjectable nativoViewholder = (NtvInjectable) holder;
            isAdContentAvailable = NativoSDK.bindAdToInjectable(nativoViewholder, recyclerView, SECTION_URL, position, null);
            return;
        }*/

        if (holder instanceof ArticleViewHolder) {
            ArticleViewHolder articleHolder = (ArticleViewHolder) holder;
            String articleTitle = articleList.get(position);
            articleHolder.bindData(position, articleTitle);
        }
        Log.d("onBindViewHolder","At pos: "+position);
    }

    @Override
    public int getItemCount() {
        //Log.d(NtvTAG, "Article Count: "+articleList.size());
        return articleList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * NativoSDK Section Adapter
     */

    @Override
    public void didReceiveAd(@NonNull String inSectionUrl, @Nullable Integer atLocation, @Nullable ViewGroup container) {
        if (atLocation == null) {
            Log.d(NtvTAG, "Did receive ad: "+ inSectionUrl);
        } else {
            Log.d(NtvTAG, "Did receive ad at: "+ atLocation +" in Section: "+ inSectionUrl);
        }
    }

    @Override
    public void didAssignAdToLocation(int location, @NonNull NtvAdData adData, @NonNull String inSection, @NonNull ViewGroup container) {
        Log.d(NtvTAG, "didAssignAdToLocation: "+location);
        articleList.add(location, "Nativo placeholder "+location);
        notifyItemInserted(location);
    }

    @Override
    public void didPlaceAdInView(@NonNull View view, @NonNull NtvAdData adData, @NonNull NtvInjectable adInjectable, int atLocation, @NonNull String inSection, @NonNull ViewGroup container) {
        Log.d(NtvTAG, "didPlaceAdInView: "+view+" Location: "+atLocation +" AdData: "+adData);
    }

    @Override
    public void didFailAd(@NonNull String inSection, @Nullable Integer atLocation, @Nullable ViewGroup container, @Nullable Throwable error) {
        Log.d(NtvTAG, "onFail Reloading data. Index: "+atLocation);
        if (atLocation != null) {
            articleList.remove(atLocation.intValue());
            notifyItemRemoved(atLocation);
        }
    }

    @Override
    public void needsDisplayLandingPage(@NonNull Intent landingPageIntent, @NonNull String inSection, @NonNull ViewGroup container) {
        context.startActivity(landingPageIntent);
    }

    @Override
    public void needsDisplayClickOutURL(@NonNull String url, @NonNull String inSection, @NonNull ViewGroup container) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Nullable
    @Override
    public Class<NtvInjectable> registerInjectableClassForTemplateType(@NonNull NtvInjectableType injectableType, @Nullable Integer atLocation, @Nullable String inSection) {
        return null;
    }
}
