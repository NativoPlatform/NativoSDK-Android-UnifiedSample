package com.nativo.sampleapp.ViewAdapter

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
import net.nativo.sdk.*
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

/**
 * Example of Nativo SDK implemented using [GridView]
 * Ads are placed according to rule in link`shouldPlaceAdAtIndex()`.
 * If an ad is not placed(eg no fill scenario) the cell is marked with red
 */
class GridViewAdapter(
    val context: Context, private val gridView: GridView
) : BaseAdapter(), NtvSectionAdapter {

    companion object {
        const val ITEM_COUNT = 20
    }

    private val sudoArticleList = mutableListOf<String>()
    private val nativoAdIndexes = mutableListOf(1, 3, 5, 7, 9, 11, 13, 15)
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
        NativoSDK.initSectionWithAdapter(this, AppConstants.SECTION_URL, context)

        for (i in 0..(ITEM_COUNT + nativoAdIndexes.size)) {
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
        val cellView: View
        if (nativoAdIndexes.contains(index)) {
            // We need a blank view for Nativo ads.
            cellView = if (view !is NativoLayout) NativoLayout(context) else view
            NativoSDK.placeAdInView(cellView, gridView, AppConstants.SECTION_URL, index, null)
        } else if (view == null || view is NativoLayout) {
            // Create new publisher article view
            cellView = LayoutInflater.from(context).inflate(R.layout.publisher_article, viewGroup, false)
            bindView(cellView, sudoArticleList[index])
        } else {
            cellView = view
            bindView(cellView, sudoArticleList[index])
        }
        return cellView
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
        notifyDataSetChanged()
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
        Log.d(NtvTAG, "onFail at location: $atLocation Error: $error")
        if (atLocation != null) {
            Log.w(NtvTAG, "Removing Nativo Ad!")
            sudoArticleList.removeAt(atLocation)
            nativoAdIndexes.remove(atLocation);
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