package com.nativo.sampleapp.ViewAdapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nativo.sampleapp.R
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.AppConstants.CLICK_OUT_URL
import com.nativo.sampleapp.util.AppConstants.SECTION_URL
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvAdData
import net.nativo.sdk.NtvSectionAdapter
import net.nativo.sdk.injectable.NtvInjectable
import net.nativo.sdk.injectable.NtvInjectableType

class ListViewAdapter(private val context: Context, private val listView: ViewGroup) : BaseAdapter(), NtvSectionAdapter {

    companion object {
        const val ITEM_COUNT = 30
    }

    var integerList: MutableList<Int> = ArrayList()
    var initialNativoRequest = true

    init {
        // Nativo init
        NativoSDK.initSectionWithAdapter(this, SECTION_URL, context)

        for (i in 0..ITEM_COUNT) {
            integerList.add(i)
        }
    }

    override fun getCount(): Int {
        return integerList.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val convertView: View = view
            ?: LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
        bindView(convertView, integerList[i], i)
        return convertView
    }

    private fun bindView(container: View, item: Int, i: Int) {
        val articleImage = container.findViewById<ImageView>(R.id.article_image)
        val sponsoredIndicator = container.findViewById<ImageView>(R.id.sponsored_ad_indicator)
        val articleAuthor = container.findViewById<TextView>(R.id.article_author)
        val articleTitle = container.findViewById<TextView>(R.id.article_title)
        val sponsoredTag = container.findViewById<TextView>(R.id.sponsored_tag)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)
        sponsoredTag?.visibility = View.INVISIBLE

        container.setOnClickListener {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(CLICK_OUT_URL)
                )
            )
        }

        if (shouldShowPlaceAd(item)) {
            val isAdContentAvailable =
                NativoSDK.placeAdInView(container, listView, SECTION_URL, i, null)
            Log.w(AppConstants.NtvTAG, "isAdContentAvailable = $isAdContentAvailable")
        }
    }

    private fun shouldShowPlaceAd(item: Int): Boolean {
        return item % 3 == 1
    }

    override fun didReceiveAd(didGetFill: Boolean, inSection: String) {
        Log.d(AppConstants.NtvTAG, "Did receive ad: $didGetFill")

        if (didGetFill && initialNativoRequest) {
            Log.w(AppConstants.NtvTAG, "Needs Reload Everything")
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
        Log.d(AppConstants.NtvTAG, "didAssignAdToLocation: $location")
    }

    override fun didPlaceAdInView(
        view: View,
        adData: NtvAdData,
        injectable: NtvInjectable,
        atLocation: Int,
        inSection: String,
        container: ViewGroup
    ) {
        Log.d(AppConstants.NtvTAG, "didPlaceAdInView: $atLocation AdData: $adData")
    }

    override fun didFailAd(
        inSection: String,
        atLocation: Int?,
        inView: View?,
        container: ViewGroup?,
        error: Throwable?
    ) {
        Log.d(AppConstants.NtvTAG, "onFail at location: $atLocation Error: $error")
        if (atLocation != null && inView != null) {
            Log.w(AppConstants.NtvTAG, "Removing Nativo Ad!")
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
