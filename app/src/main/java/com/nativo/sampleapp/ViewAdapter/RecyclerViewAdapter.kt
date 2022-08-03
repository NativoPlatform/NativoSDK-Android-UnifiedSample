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
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

const val ITEM_TYPE_ARTICLE = 0
const val ITEM_TYPE_NATIVO = 1
const val ITEM_COUNT = 40

class RecyclerViewAdapter(private val context: Context, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), NtvSectionAdapter {

    private val articleList: ArrayList<String> = ArrayList()
    private var nativoNeedsReload = false

    private var onClickListener = View.OnClickListener {
        it.context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }

    init {
        // This initializes the NativoSDK and starts prefetching ads for your section URL
        NativoSDK.initSectionWithAdapter(this, AppConstants.SECTION_URL, context)
        // Enable this since we have placeholders for Nativo in our data set
        NativoSDK.enablePlaceholderMode(true)
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

    // Helper method to determine which indexes should be Nativo ads
    private fun shouldPlaceNativoAdAtIndex(i: Int): Boolean {
        return i % 3 == 1
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
        Log.d(NtvTAG, "Did receive ad: $didGetFill")

        // Wait until we have response from Nativo to create articleList
        if (articleList.size == 0) {
            createArticlesDataSet()
        }
    }

    override fun didAssignAdToLocation(location: Int, adData: NtvAdData, inSection: String, container: ViewGroup) {
        Log.d(NtvTAG, "didAssignAdToLocation: $location")
    }

    override fun didFailAd(inSection: String, atLocation: Int?, inView: View?, container: ViewGroup?, error: Throwable?) {
        Log.d(NtvTAG, "onFail at location: $atLocation Error: $error")
        if (atLocation != null) {
            Log.w(NtvTAG,"Removing Nativo Ad!")
            articleList.removeAt(atLocation)
            notifyItemRemoved(atLocation)
        }

        // Add this here in case Nativo fails, we still create our article list
        if (articleList.size == 0) {
            createArticlesDataSet()
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