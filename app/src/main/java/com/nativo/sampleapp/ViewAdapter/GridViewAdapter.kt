package com.nativo.sampleapp.ViewAdapter

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

class GridViewAdapter(
    context: Context, private val gridView: GridView
) : ArrayAdapter<Int>(context, R.layout.publisher_article),
    NtvSectionAdapter {

    companion object {
        const val ITEM_COUNT = 30
    }

    private var integerList: MutableList<Int> = ArrayList()
    private var initialNativoRequest = true

    /**
     * Show [CLICK_OUT_URL]
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

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        // Publisher article view
        val convertView: View
        val holder: ItemViewHolder

        if (view == null) {
            convertView = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.publisher_article, viewGroup, false)
            holder = ItemViewHolder(convertView)
            convertView.tag = holder
        } else {
            convertView = view
            holder = view.tag as ItemViewHolder
        }

        holder.bind(integerList[i], i)
        return convertView
    }

    inner class ItemViewHolder(private val view: View) {
        private val articleImage: ImageView? = view.findViewById(R.id.article_image)
        private val articleTitle: TextView? = view.findViewById(R.id.article_title)
        private val articleAuthor: TextView? = view.findViewById(R.id.article_description)
        private val articleSponsor: ImageView? = view.findViewById(R.id.sponsored_ad_indicator)

        fun bind(item: Int, position: Int) {
            articleImage?.setImageResource(R.drawable.newsimage)
            articleSponsor?.visibility = View.INVISIBLE
            articleAuthor?.setText(R.string.sample_author)
            articleTitle?.setText(R.string.sample_title)

            if (shouldShowPlaceAd(item)) {
                val isAdContentAvailable =
                    NativoSDK.placeAdInView(view, gridView, AppConstants.SECTION_URL, position, null)
            } else {
                view.setOnClickListener {
                    itemClickListener.run()
                }
            }
        }

        private fun shouldShowPlaceAd(item: Int): Boolean {
            return item % 3 == 1
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