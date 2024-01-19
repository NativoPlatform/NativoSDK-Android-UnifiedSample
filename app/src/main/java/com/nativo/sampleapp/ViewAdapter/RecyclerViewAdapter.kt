package com.nativo.sampleapp.ViewAdapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder
import com.nativo.sampleapp.util.AppConstants.NtvTAG
import com.nativo.sampleapp.util.AppConstants.SECTION_URL
import net.nativo.sdk.NativoSDK.enablePlaceholderMode
import net.nativo.sdk.NativoSDK.initSectionWithAdapter
import net.nativo.sdk.NativoSDK.placeAdInView
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType
import net.nativo.sdk.utils.NativoViewHolder

class RecyclerViewAdapter(context: Context, recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), NtvSectionAdapter {
    private val context: Context
    private val recyclerView: RecyclerView
    var articleList = ArrayList<String>()

    // Helper method to determine which indexes should be Nativo ads
    fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        if (i > 7) {
            return i % 3 == 0
        }
        return false
    }

    /**
     * LEGACY
     */
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
    val ITEM_TYPE_ARTICLE = 0
    val ITEM_TYPE_NATIVO = 1

    init {
        // Nativo init
        initSectionWithAdapter(this, SECTION_URL, context)
        enablePlaceholderMode(true)
        this.context = context
        this.recyclerView = recyclerView

        // Create sudo articles datasource
        for (i in 0..29) {
            if (shouldPlaceNativoAdAtIndex(i)) {
                val title = "Nativo Placeholder"
                articleList.add(title)
            } else {
                val title = "Publisher Article $i"
                articleList.add(title)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (articleList[position].contains("Nativo")) {
            ITEM_TYPE_NATIVO
        } else {
            ITEM_TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewTypeInt: Int
    ): RecyclerView.ViewHolder {
        // TODO: Inflate empty view for Nativo ads. No need to check ad type. Then inflate previously registered layout and inject into view.
        return if (viewTypeInt == ITEM_TYPE_NATIVO) {
            Log.d(NtvTAG, "Created new NativoViewHolder")
            NativoViewHolder(viewGroup.context)
        } else {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
            ArticleViewHolder(view, viewGroup)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var isAdContentAvailable = false
        if (holder is NativoViewHolder) {
            Log.d(NtvTAG, "placing ad at position $position")
            isAdContentAvailable =
                placeAdInView(holder.itemView, recyclerView, SECTION_URL, position, null)
            if (!isAdContentAvailable) {
                Log.d(NtvTAG, "Couldn't place ad! at $position")
            }
        }

        // LEGACY
        /*boolean isAdContentAvailable = false;
        if (holder instanceof NtvInjectable) {
            NtvInjectable nativoViewholder = (NtvInjectable) holder;
            isAdContentAvailable = NativoSDK.bindAdToInjectable(nativoViewholder, recyclerView, SECTION_URL, position, null);
            return;
        }*/if (holder is ArticleViewHolder) {
            val articleTitle = articleList[position]
            holder.bindData(position, articleTitle)
        }
        Log.d("onBindViewHolder", "At pos: $position")
    }

    override fun getItemCount(): Int {
        //Log.d(NtvTAG, "Article Count: "+articleList.size());
        return articleList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * NativoSDK Section Adapter
     */
    override fun didReceiveAd(didGetAdFill: Boolean, inSectionUrl: String) {
        Log.d(NtvTAG, "Did receive ad: $didGetAdFill")
    }

    override fun didAssignAdToLocation(
        location: Int,
        adData: NtvAdData,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d(NtvTAG, "didAssignAdToLocation: $location")

        // Reload initial views to inject ad. Subsequent views will already have ad prefetched.
        notifyItemChanged(location)
    }

    override fun didPlaceAdInView(
        view: View,
        adData: NtvAdData,
        injectableTemplate: NtvInjectable,
        atLocation: Int,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d(NtvTAG, "didPlaceAdInView: $atLocation AdData: $adData")
    }

    override fun didFailAd(
        inSection: String,
        atLocation: Int?,
        inView: View?,
        container: ViewGroup?,
        error: Throwable?
    ) {
        Log.d(NtvTAG, "onFail at location: $atLocation Error: $error")
        if (atLocation != null) {
            Log.w(NtvTAG, "Removing Nativo Ad!")
            if (inView != null) {
                inView.visibility = View.GONE
                //((ViewGroup)inView).getChildAt(0).setVisibility(View.GONE);
            }
            //            articleList.remove(atLocation.intValue());
//            notifyItemRemoved(atLocation);
        }
    }

    override fun needsDisplayLandingPage(
        landingPageIntent: Intent,
        inSection: String,
        container: ViewGroup
    ) {
        context.startActivity(landingPageIntent)
    }

    override fun needsDisplayClickOutURL(url: String, inSection: String, container: ViewGroup) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun <T : NtvInjectable> registerInjectableClassForTemplateType(
        injectableType: NtvInjectableType,
        atLocation: Int?,
        inSection: String?
    ): Class<T>? {
        return null
    }

}