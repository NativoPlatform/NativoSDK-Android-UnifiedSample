package com.nativo.sampleapp.ViewAdapter

import android.annotation.SuppressLint
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
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.AppConstants.NtvTAG
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.NtvTestAdType
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

const val ITEM_TYPE_ARTICLE = 0
const val ITEM_TYPE_NATIVO = 1
const val ITEM_COUNT = 22

class RecyclerViewAdapter(private val context: Context, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), NtvSectionAdapter {

    private val articleList: ArrayList<String> = ArrayList()
    private val adRequestOptions: MutableMap<Int, Map<String,String>> = mutableMapOf()
    private var adRequestCount = 0

    init {
        NativoSDK.enableTestAdvertisements(NtvTestAdType.DISPLAY)
        // This initializes the NativoSDK and starts prefetching ads for your section URL
        NativoSDK.initSectionWithAdapter(this, AppConstants.SECTION_URL, context)
        // Enable this since we have placeholders for Nativo in our data set
        NativoSDK.enablePlaceholderMode(true)

        createArticlesDataSet()
    }

    // Helper method to determine which indexes should be Nativo ads
    private fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 2 == 1
    }
    /**
     * Create article list as our data set
     * Wait until we have response from Nativo before creating
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun createArticlesDataSet() {
        // Create artificial datasource with both article and Nativo ad items
        var articleNum = 0
        var adNum = 0
        for ( i in 0 until ITEM_COUNT) {
            val title = if (shouldPlaceNativoAdAtIndex(i)) {
                adNum++
                "Nativo Placeholder $adNum"
            } else {
                articleNum++
                "Publisher Article $articleNum"
            }
            articleList.add(title)
        }
        notifyDataSetChanged()
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
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_NATIVO) {
            // Use this Nativo helper class to easily create ViewHolders for Nativo ads
            net.nativo.sdk.utils.NativoViewHolder(viewGroup.context)
        } else {
            val view: View = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
            ArticleViewHolder(view, viewGroup)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is net.nativo.sdk.utils.NativoViewHolder) {
            // Call Nativo placeAdInView using NativoViewHolder.itemView
            val success = NativoSDK.placeAdInView(holder.itemView,
                                                  recyclerView,
                                                  AppConstants.SECTION_URL,
                                                  position,
                                                  null)
            Log.d(NtvTAG, "placing ad at position $position, available: $success")
        } else if (holder is ArticleViewHolder) {
            val articleTitle = articleList[position]
            holder.bindData(position, articleTitle)
        }
        Log.d("onBindViewHolder", "At pos: $position")
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    /**
     * Nativo Section Adapter Interface
     */

    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        /*
        * Benchmark logic
        * Both v6 and v7 should return same number of ads,
        * Two of each type in order to measure performance of how cells can be reused
         */
        adRequestCount++
        when (adRequestCount) {
            1 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.NATIVE)
            2 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.VIDEO_CLICK_TO_PLAY)
//            3 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.DISPLAY)
            3 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.STORY)
            4 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.VIDEO_SCROLL_TO_PLAY)
            5 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.NATIVE)
            6 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.VIDEO_CLICK_TO_PLAY)
            7 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.STORY)
            8 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.VIDEO_SCROLL_TO_PLAY)
            9 -> NativoSDK.enableTestAdvertisements(NtvTestAdType.DISPLAY)
            else -> NativoSDK.enableTestAdvertisements(NtvTestAdType.NO_FILL)
        }
        Log.d(NtvTAG, "Did receive ad: $didGetFill")
    }

    override fun didAssignAdToLocation(location: Int, adData: NtvAdData, inSection: String, container: ViewGroup) {
        Log.d(NtvTAG, "didAssignAdToLocation: $location")
        if (location < 4) {
            notifyItemChanged(location)
        }
    }

    override fun didFailAd(inSection: String, atLocation: Int?, inView: View?, container: ViewGroup?, error: Throwable?) {
        Log.d(NtvTAG, "onFail at location: $atLocation Error: $error")
        if (atLocation != null) {
            Log.w(NtvTAG,"Removing Nativo Ad! $articleList")
            articleList.removeAt(atLocation)
            notifyItemRemoved(atLocation)
        }
    }

    override fun didPlaceAdInView(view: View, adData: NtvAdData, injectable: NtvInjectable, atLocation: Int, inSection: String, container: ViewGroup) {
        Log.d(NtvTAG, "didPlaceAdInView: $atLocation AdData: $adData")
    }

    override fun needsDisplayClickOutURL(url: String, inSection: String, container: ViewGroup) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun needsDisplayLandingPage(
        landingPageIntent: Intent,
        inSection: String,
        container: ViewGroup
    ) {
        context.startActivity(landingPageIntent)
    }

    override fun <T : NtvInjectable> registerInjectableClassForTemplateType(
        injectableType: NtvInjectableType,
        atLocation: Int?,
        inSection: String?
    ): Class<T>? {
        return null
    }
}