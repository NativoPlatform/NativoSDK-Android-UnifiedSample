package com.nativo.sampleapp.ViewAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.AppConstants.NtvTAG
import net.nativo.sdk.utils.NativoLayout
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
        const val ITEM_COUNT = 40
    }

    private var integerList: MutableList<Int> = ArrayList()
    private var initialNativoRequest = true

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

        for (i in 0..ITEM_COUNT) {
            integerList.add(i)
        }
    }

    override fun getCount(): Int {
        return integerList.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val item = integerList[i]
        val convertView: View
        if (view != null) {
            if (shouldShowPlaceAd(item)) {
                convertView = if (view !is NativoLayout) {
                    NativoLayout(context)
                } else {
                    view
                }

                NativoSDK.placeAdInView(convertView, listView, AppConstants.SECTION_URL, i, null)
                return convertView
            } else {
                // Publisher article view
                convertView = if (view is NativoLayout) {
                    LayoutInflater.from(context)
                        .inflate(R.layout.publisher_article, viewGroup, false)
                } else {
                    view
                }
                bindView(convertView, integerList[i])
            }
        } else {
            if (shouldShowPlaceAd(item)) {
                convertView = NativoLayout(context)
                NativoSDK.placeAdInView(convertView, listView, AppConstants.SECTION_URL, i, null)
            } else {
                // Publisher article view
                convertView = LayoutInflater.from(context)
                    .inflate(R.layout.publisher_article, viewGroup, false)
                bindView(convertView, integerList[i])
            }
        }

        return convertView
    }

    @SuppressLint("SetTextI18n")
    private fun bindView(view: View, item: Int) {
        val articleImage: ImageView? = view.findViewById(R.id.article_image)
        val articleTitle: TextView? = view.findViewById(R.id.article_title)
        val articleAuthor: TextView? = view.findViewById(R.id.article_description)
        val articleSponsor: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)

        articleImage?.setImageResource(R.drawable.newsimage)
        articleSponsor?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.text = context.getString(R.string.sample_title) + " :" + item

        view.setOnClickListener {
            itemClickListener.run()
        }
    }

    private fun shouldShowPlaceAd(item: Int): Boolean {
        return item % 3 == 1
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
        if (atLocation != null && inView != null) {
            Log.w(NtvTAG, "Removing Nativo Ad!")
            integerList.removeAt(atLocation.toInt())
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