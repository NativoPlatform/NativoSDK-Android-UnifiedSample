package com.nativo.sampleapp.ViewAdapter;

import static com.nativo.sampleapp.util.AppConstants.NtvTAG;
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

import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.NtvAdData;
import net.nativo.sdk.NtvSectionAdapter;
import net.nativo.sdk.injectable.NtvInjectable;
import net.nativo.sdk.injectable.NtvInjectableType;
import net.nativo.sdk.utils.NativoViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NtvSectionAdapter {

    private final Context context;
    private final RecyclerView recyclerView;
    ArrayList<String> articleList = new ArrayList<>();

    public RecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        // Nativo init
        NativoSDK.initSectionWithAdapter(this, SECTION_URL, context);
        NativoSDK.enablePlaceholderMode(true);

        this.context = context;
        this.recyclerView = recyclerView;

        // Create sudo articles datasource
        for (int i = 0; i < 30; i++) {
            if (shouldPlaceNativoAdAtIndex(i)) {
                String title = "Nativo Placeholder";
                articleList.add(title);
            } else {
                String title = "Publisher Article " + i;
                articleList.add(title);
            }
        }
    }

    // Helper method to determine which indexes should be Nativo ads
    public boolean shouldPlaceNativoAdAtIndex(int i) {
        return i % 6 == 1;
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
        if (articleList.get(position).contains("Nativo")) {
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
            Log.d(NtvTAG, "placing ad at position "+position);
            isAdContentAvailable = NativoSDK.placeAdInView(holder.itemView, recyclerView, SECTION_URL, position, null);
            if (!isAdContentAvailable) {
                Log.d(NtvTAG, "Couldn't place ad! at "+position);
            }
        }



        // LEGACY
        /*boolean isAdContentAvailable = false;
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
    public void didReceiveAd(boolean didGetAdFill, @NotNull String inSectionUrl) {
        Log.d(NtvTAG, "Did receive ad: "+ didGetAdFill);
    }

    @Override
    public void didAssignAdToLocation(int location, @NonNull NtvAdData adData, @NonNull String inSection, @NonNull ViewGroup container) {
        Log.d(NtvTAG, "didAssignAdToLocation: "+location);
        //articleList.add(location, "Nativo placeholder "+location);
        //notifyItemInserted(location);

        // Reload initial views to inject ad. Subsequent views will already have ad prefetched.
        if (location < 5) {
            Log.w(NtvTAG, "Needs Reload");
            notifyItemChanged(location);
        }
    }

    @Override
    public void didPlaceAdInView(@NonNull View view, @NonNull NtvAdData adData, @NonNull NtvInjectable injectableTemplate, int atLocation, @NonNull String inSection, @NonNull ViewGroup container) {
        Log.d(NtvTAG, "didPlaceAdInView: "+ atLocation +" AdData: "+adData);
    }

    @Override
    public void didFailAd(@NotNull String inSection, @org.jetbrains.annotations.Nullable Integer atLocation, @org.jetbrains.annotations.Nullable View inView, @org.jetbrains.annotations.Nullable ViewGroup container, @org.jetbrains.annotations.Nullable Throwable error) {
        Log.d(NtvTAG, "onFail at location: "+atLocation+" Error: "+ error);
        if (atLocation != null) {
            Log.w(NtvTAG,"Removing Nativo Ad!");
            if (inView != null) {
                inView.setVisibility(View.GONE);
                //((ViewGroup)inView).getChildAt(0).setVisibility(View.GONE);
            }
//            articleList.remove(atLocation.intValue());
//            notifyItemRemoved(atLocation);
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
