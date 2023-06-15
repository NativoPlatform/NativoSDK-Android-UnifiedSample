package com.nativo.sampleapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nativo.sampleapp.ViewHolders.ArticleViewHolder
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.AppConstants.NtvTAG
import com.nativo.sampleapp.util.Reloadable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

const val ITEM_TYPE_ARTICLE = 0
const val ITEM_TYPE_NATIVO = 1
const val ITEM_COUNT = 20

class RecyclerViewAdapter(private val context: Context, private val recyclerView: RecyclerView, val nativoSection : String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), NtvSectionAdapter, Reloadable {

    private val articleList: ArrayList<String> = ArrayList()
    private var needsReload = true

    init {
        createDataSet()
        // This initializes the NativoSDK and starts prefetching ads for your section URL
        NativoSDK.initSectionWithAdapter(this, nativoSection, context)
        // Enable this since we have placeholders for Nativo in our data set
        NativoSDK.enablePlaceholderMode(true)
    }

    override fun reload() {
        Log.d("NativoApp", "Reloading dataset")
        needsReload = true
        createDataSet()
        NativoSDK.clearAds(nativoSection)
        NativoSDK.initSectionWithAdapter(this, nativoSection, context)
        NativoSDK.prefetchAdForSection(nativoSection)
    }

    // Helper method to determine which indexes should be Nativo ads
    private fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 3 == 1
    }

    /**
     * Create article list as our data set
     * Wait until we have response from Nativo before creating
     */
    @SuppressLint("NotifyDataSetChanged")
    fun createDataSet() {
        articleList.clear()
        // Create artificial datasource with both article and Nativo ad placeholders
        var articleNum = 0
        var adNum = 0
        for ( i in 0 until ITEM_COUNT) {
            val title = if (shouldPlaceNativoAdAtIndex(i)) {
                adNum++
                "Nativo Placeholder $adNum"
            } else {
                articleNum++
                "Article"
            }
            articleList.add(title)
        }
        Log.i("Ntv", "Created new Dataset: $articleList")
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
                                                  nativoSection,
                                                  position,
                                           null)
        } else if (holder is ArticleViewHolder) {

        }
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
        Log.d(NtvTAG, "Did receive ad: $didGetFill")
    }

    override fun didAssignAdToLocation(location: Int, adData: NtvAdData, inSection: String, container: ViewGroup) {
        Log.d(NtvTAG, "didAssignAdToLocation: $location")
        notifyItemChanged(location)
    }

    override fun didFailAd(inSection: String, atLocation: Int?, inView: View?, container: ViewGroup?, error: Throwable?) {
        Log.d(NtvTAG, "onFail at location: $atLocation Error: ${error?.message}")
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