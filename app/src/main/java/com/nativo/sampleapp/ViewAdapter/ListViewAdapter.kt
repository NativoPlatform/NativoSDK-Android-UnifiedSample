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
import com.nativo.sampleapp.util.AppConstants.NtvTAG
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

    /**
     * Show [CLICK_OUT_URL] as a default listener
     */
    private var _itemClickListener: ((item: Int) -> Unit)? = {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(CLICK_OUT_URL)
            )
        )
    }

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

    private fun bindView(view: View, item: Int, i: Int) {
        val articleImage = view.findViewById<ImageView>(R.id.article_image)
        val sponsoredIndicator = view.findViewById<ImageView>(R.id.sponsored_ad_indicator)
        val articleAuthor = view.findViewById<TextView>(R.id.article_author)
        val articleTitle = view.findViewById<TextView>(R.id.article_title)
        val sponsoredTag = view.findViewById<TextView>(R.id.sponsored_tag)

        articleImage?.setImageResource(R.drawable.newsimage)
        sponsoredIndicator?.visibility = View.INVISIBLE
        articleAuthor?.setText(R.string.sample_author)
        articleTitle?.setText(R.string.sample_title)
        sponsoredTag?.visibility = View.INVISIBLE

        if (shouldShowPlaceAd(item)) {
            val isAdContentAvailable =
                NativoSDK.placeAdInView(view, listView, SECTION_URL, i, null)
            Log.w(NtvTAG, "isAdContentAvailable = $isAdContentAvailable")
        } else {
            _itemClickListener?.invoke(item)
        }
    }

    fun setItemClickListener(listener: ((item: Int) -> Unit)?) {
        _itemClickListener = listener
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
