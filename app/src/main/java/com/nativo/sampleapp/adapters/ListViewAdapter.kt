package com.nativo.sampleapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.AppConstants.NtvTAG
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

/**
 * Example of Nativo SDK implemented using [ListView]
 * Ads are placed according to rule in link`shouldPlaceAdAtIndex()`.
 * If an ad is not placed(eg no fill scenario) the cell is marked with red
 */
class ListViewAdapter(
    private val context: Context, private val listView: ListView
) : BaseAdapter(), NtvSectionAdapter {

    companion object {
        const val ITEM_COUNT = 20
    }

    private var sudoArticleList = mutableListOf<String>()
    private var initialNativoRequest = true

    // Create unique layout class for Nativo ads
    private class NativoLayout(context: Context) : FrameLayout(context)

    /**
     * Show [AppConstants.CLICK_OUT_URL]
     */
    private var itemClickListener = Runnable {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(AppConstants.CLICK_OUT_URL)
            )
        )
    }

    init {
        // Nativo init
        NativoSDK.clearAds(AppConstants.SECTION_URL, listView)
        NativoSDK.initSectionWithAdapter(this, AppConstants.SECTION_URL, context)

        // Create article list
        for (i in 0..ITEM_COUNT) {
            sudoArticleList.add("Article $i")
        }
    }

    override fun getCount(): Int {
        return sudoArticleList.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(index: Int, view: View?, viewGroup: ViewGroup): View {
        var itemView : View

        // Create Nativo ad
        if (isNativoRow(index)) { // Check if index should be a Nativo ad slot
            itemView = if (view !is NativoLayout) NativoLayout(context) else view
            val nativoSuccess = NativoSDK.placeAdInView(itemView, listView, AppConstants.SECTION_URL, index, null)

            // In this example we need to check if Nativo was successful before returning the view.
            // Otherwise we may end up with a blank ad unit if an ad fails.
            if (nativoSuccess) {
                return itemView
            }
        }

        // Create Article
        if (view == null || view is NativoLayout) {
            // Create new publisher article view
            itemView = LayoutInflater.from(context).inflate(R.layout.publisher_article, viewGroup, false)
            bindView(itemView, sudoArticleList[index])
        } else {
            itemView = view
            bindView(itemView, sudoArticleList[index])
        }
        return itemView
    }

    private fun isNativoRow(item: Int): Boolean {
        return item % 3 == 1
    }

    @SuppressLint("SetTextI18n")
    private fun bindView(view: View, item: String) {
        val articleImage: ImageView? = view.findViewById(R.id.article_image)
        val articleTitle: TextView? = view.findViewById(R.id.article_title)
        val articleAuthor: TextView? = view.findViewById(R.id.article_description)

        articleImage?.setImageResource(R.drawable.newsimage)
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.text = item

        view.setOnClickListener {
            itemClickListener.run()
        }
    }

    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        Log.d(NtvTAG, "Did receive ad: $didGetFill")

        if (didGetFill && initialNativoRequest) {
            Log.w(NtvTAG, "Needs Reload Everything")
            notifyDataSetChanged()
        }

        initialNativoRequest = false
    }

    override fun didAssignAdToLocation(
        location: Int,
        adData: NtvAdData,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d(NtvTAG, "didAssignAdToLocation: $location")
        sudoArticleList.add(location, "Nativo $location")
    }

    override fun didPlaceAdInView(
        view: View,
        adData: NtvAdData,
        injectable: NtvInjectable,
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
        if (atLocation != null) {
            Log.w(NtvTAG, "Removing Nativo Ad!")
            sudoArticleList.removeAt(atLocation)
            notifyDataSetChanged()
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